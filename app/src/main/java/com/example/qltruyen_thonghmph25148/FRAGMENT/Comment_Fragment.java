package com.example.qltruyen_thonghmph25148.FRAGMENT;

import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qltruyen_thonghmph25148.ADAPTER.CommentAdapter;
import com.example.qltruyen_thonghmph25148.MODEL.Comics;
import com.example.qltruyen_thonghmph25148.MODEL.Comment;
import com.example.qltruyen_thonghmph25148.MODEL.INTERFACE.Comics_Interface;
import com.example.qltruyen_thonghmph25148.MODEL.INTERFACE.Comment_Interface;
import com.example.qltruyen_thonghmph25148.MODEL.UserData;
import com.example.qltruyen_thonghmph25148.R;
import com.example.qltruyen_thonghmph25148.welcome.NotifyConfig;
import com.example.qltruyen_thonghmph25148.welcome.SignUpActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Comment_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Comment_Fragment extends Fragment {
    private Comics comic; // Thêm biến để lưu thông tin Commic
    private UserData userData;
    private List<UserData> userDataList;
    private View view; // Khai báo biến view ở đây
    private RecyclerView recyclerViewComments;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    static final String BASE_URL = "http://10.0.2.2:3000/api/";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Socket mSocket;
    {
        try {
            mSocket= IO.socket("http://10.0.2.2:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public Comment_Fragment() {
        // Required empty public constructor
    }

    public static Comment_Fragment newInstance(Comics comic, UserData userData, List<UserData> userDataList) {
        Comment_Fragment fragment = new Comment_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("COMIC_EXTRA", comic);
        args.putParcelable("USER_DATA_EXTRA", userData);
        args.putParcelableArrayList("USER_DATA_LIST_EXTRA", new ArrayList<>(userDataList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comic = getArguments().getParcelable("COMIC_EXTRA");
            userData = getArguments().getParcelable("USER_DATA_EXTRA");
            userDataList = getArguments().getParcelableArrayList("USER_DATA_LIST_EXTRA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_comment_, container, false);

        mSocket.connect();
        //lắng nghe sự kiện

        mSocket.on("new msg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data_sv_send= (String) args[0];
                        Toast.makeText(getActivity(), "Server trả về "+data_sv_send, Toast.LENGTH_SHORT).show();

                        //hiện notify lên status
                        postNotify("Bình luận thành công",data_sv_send);
                    }
                });
            }
        });


        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList,userDataList);
        recyclerViewComments.setAdapter(commentAdapter);
        if (comic != null) {

        }
        // TODO: Xử lý các sự kiện khi click vào các nút button (nếu cần)
        getCommentsByComicId(comic.get_id());  // Gọi API để lấy danh sách comment
        ImageView btnAddComment = view.findViewById(R.id.btncm);
        EditText editTextComment = view.findViewById(R.id.edtcm);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData == null) {
//                    Log.d("eeee", "onClick: "+comic.get_id());
//                    Log.d("eeee", "userData: "+userData.get_id());
                    Toast.makeText(getActivity(), "Cần đăng nhập mới bình luận được", Toast.LENGTH_SHORT).show();

                } else {

                    // Lấy thời gian thực
                    Calendar now = Calendar.getInstance();

// Format thời gian theo chuỗi
                    String formattedTime = String.format(
                            "%04d-%02d-%02d %02d:%02d:%02d",
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH) + 1, // Tháng trong Java Calendar bắt đầu từ 0, nên cần +1
                            now.get(Calendar.DAY_OF_MONTH),
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND)
                    );
                    // Thêm bình luận theo id truyện
                    String commentContent = editTextComment.getText().toString().trim();
                    if (commentContent.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        // Gọi API để thêm bình luận mới
                        addComment(comic.get_id(), userData.get_id(), commentContent,formattedTime);
                    }
                    // Hiển thị thông báo "Cần đăng nhập mới bình luận được"
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void getCommentsByComicId(String idComic) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Comment_Interface commentInterface = retrofit.create(Comment_Interface.class);

        // Gọi API lấy danh sách comment theo ID truyện
        Call<List<Comment>> call = commentInterface.getCommentsByComicId(idComic);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> comments = response.body();
                    if (comments != null && !comments.isEmpty()) {
                        commentList.addAll(comments);
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        // Hiển thị thông báo không có bình luận nào
                    }
                } else {
                    // Hiển thị thông báo lỗi
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                // Hiển thị thông báo lỗi
            }
        });
    }


    private void addComment(String id_comic, String id_user, String commentContent,String timcm) {
        // Tạo Retrofit và interface để gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Comment_Interface commentInterface = retrofit.create(Comment_Interface.class);

        // Gọi API để thêm bình luận mới
        Call<Comment> call = commentInterface.addComment(id_comic, id_user, commentContent,timcm);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    // Cập nhật danh sách bình luận sau khi thêm thành công
                    Comment newComment = response.body();
                    List<Comment> newComments = new ArrayList<>(commentList);
                    newComments.add(newComment);
                    commentAdapter.updateCommentList(newComments);
                    mSocket.emit("new msg","Bạn "+ userData.getUserName()+" vừa bình luận truyện: "+comic.getName());
                } else {
                    // Hiển thị thông báo lỗi khi thêm bình luận không thành công
                    Log.d("eeeeiii", "onResponse: "+ response.message());
                    Toast.makeText(getActivity(), "Lỗi khi thêm bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                // Hiển thị thông báo lỗi khi gọi API thêm bình luận thất bại
                Toast.makeText(getActivity(), "Lỗi khi gọi API thêm bình luận", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(getActivity(), NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(getActivity(), "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }
}