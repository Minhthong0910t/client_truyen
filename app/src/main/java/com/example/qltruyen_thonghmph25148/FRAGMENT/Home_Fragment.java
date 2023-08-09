package com.example.qltruyen_thonghmph25148.FRAGMENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.qltruyen_thonghmph25148.ADAPTER.ComicsAdapter;
import com.example.qltruyen_thonghmph25148.Chitiet_Activity;
import com.example.qltruyen_thonghmph25148.MODEL.Comics;
import com.example.qltruyen_thonghmph25148.MODEL.INTERFACE.Comics_Interface;
import com.example.qltruyen_thonghmph25148.MODEL.Photo;
import com.example.qltruyen_thonghmph25148.MODEL.UserData;
import com.example.qltruyen_thonghmph25148.R;
import com.example.qltruyen_thonghmph25148.ADAPTER.viewpager2.SlideAdapterHome;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ViewPager vpr;
    private SearchView searchView;
    private RecyclerView rcvComic;
    private ComicsAdapter comicsAdapter;
    private CircleIndicator circleIndicator;
    private SlideAdapterHome slideAdapter;
    private Timer timer;
    private List<Photo> photoList;
    private Animation animation;
    private Handler handler = new Handler(Looper.getMainLooper());
    List<Comics> list_comic;
    static final  String BASE_URL="http://10.0.2.2:3000/api/";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    UserData userData;

    public Home_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home_Fragment newInstance(UserData userData) {
        Home_Fragment fragment = new Home_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userData = getArguments().getParcelable("userData");
            if (userData != null) {
                String username = userData.getUserName();
                String userId = userData.get_id();
                // Tiếp tục xử lý dữ liệu username và userId theo ý muốn
            }
        }
    }
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home_, container, false);

        anhXa();
//        getData();
        getComic();
        searchComic();
        // Inflate the layout for this fragment
        return view;
    }

    private void anhXa(){
        //slide
        vpr = (ViewPager) view.findViewById(R.id.vpr);
        searchView=view.findViewById(R.id.seachView);
        searchView.clearFocus();
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator);
        photoList = getListPhoto();
        slideAdapter = new SlideAdapterHome(getContext(), photoList);
        vpr.setAdapter(slideAdapter);
        circleIndicator.setViewPager(vpr);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlide();
        // list comic
        rcvComic=view.findViewById(R.id.rcv_ComicList);
        //
        list_comic= new ArrayList<Comics>();




    }
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner4));
        list.add(new Photo(R.drawable.banner3));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner1));
        list.add(new Photo(R.drawable.banner));
        return list;
    }
    private void autoSlide() {
        if (photoList == null || photoList.isEmpty() || vpr == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItem = vpr.getCurrentItem();
                        int toltalItem = photoList.size() - 1;
                        if (curentItem < toltalItem) {
                            curentItem++;
                            vpr.setCurrentItem(curentItem);
                        } else {
                            vpr.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 3000, 4000);
    }

    private void searchComic(){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterliss(newText);
                    return true;
                }



                private void filterliss(String Text) {
                    List<Comics> fiteliss = new ArrayList<>();
                    for (Comics comic : list_comic) {
                        if (comic.getName().toLowerCase().contains(Text.toLowerCase())) {
                            fiteliss.add(comic);
                        }
                    }
                    if (fiteliss.isEmpty()) {

                    } else {
                        comicsAdapter.setfilterliss(fiteliss);
                    }
                }
            });
    }
    private void getComic(){
        // tạo gson
        Gson gson= new GsonBuilder().setLenient().create();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // sử dụng interface

        Comics_Interface comicInterface=retrofit.create(Comics_Interface.class);

        //tạo đối tượng
        Call<List<Comics>> objCall=comicInterface.danh_sach_comic();
        objCall.enqueue(new Callback<List<Comics>>() {
            @Override
            public void onResponse(Call<List<Comics>> call, Response<List<Comics>> response) {
                if (response.isSuccessful()){
                    list_comic.clear();
                    list_comic.addAll(response.body());
                    Log.d("zzzzzzzz", "onResponse: "+list_comic);
                    Log.d("zzzzzzzzzzzz", "onResponse: "+ response.body());
//                    comicsAdapter.notifyDataSetChanged();

                    //tạo adapter đổ lên listview
//                    comicsAdapter = new ComicsAdapter(getActivity(),list_comic);
                    comicsAdapter = new ComicsAdapter(getActivity(), list_comic, new ComicsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Comics comic) {
                            // Lấy thông tin chi tiết của item được bấm và chuyển sang Activity mới
                            String selectedItemId = comic.get_id();
                            Intent intent = new Intent(getActivity(), Chitiet_Activity.class);
                            intent.putExtra("COMIC_EXTRA", comic);
                            intent.putExtra("USER_DATA_EXTRA", userData); // Truyền userData qua Intent
                            Log.d("zzz"," " + comic);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                    rcvComic.setLayoutManager(gridLayoutManager);
                    rcvComic.setAdapter(comicsAdapter);
                }
                else {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu" +response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comics>> call, Throwable t) {
                Log.e("RetrofitError", "onFailure: ", t);
                Toast.makeText(getActivity(), "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }




    @Override
    public void onResume() {
        super.onResume();
        getComic();
    }



}







//getdata
//    private void getData(){
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                String dia_chi = "http://10.0.2.2:3000/api/comic";
//                String noidung = "";
//
//                //1. Tạo đối tượng url
//                try {
//                    URL url = new URL( dia_chi );
//                    //2. Mở kết nối
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    //3. Tạo đối tượng đọc luồng dữ liệu
//                    InputStream inputStream = conn.getInputStream();
//                    //4. Tạo biến đê đọc dữ liệu
//                    BufferedReader reader = new BufferedReader( new InputStreamReader(  inputStream )   );
//                    //5. Tạo biến ghép nối dữ liệu
//                    StringBuilder builder = new StringBuilder();
//                    String dong; // dòng dữ liệu đọc được
//                    // đọc dữ liệu
//                    while(  (dong = reader.readLine()) != null  ){
//                        builder.append( dong ).append("\n");
//                    }
//                    // kết thúc quá trình đọc:
//                    reader.close();
//                    inputStream.close();
//                    conn.disconnect();
//                    noidung = builder.toString();
//
//                } catch (MalformedURLException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                //----- lấy xong dữ liệu, xử lý hiển thị lên giao diện
//
//
//                String finalNoidung = noidung;
//                Log.d("Data", " " + finalNoidung);
//                Gson gson = new Gson();
//                DataComics_api data_api = gson.fromJson(finalNoidung,DataComics_api.class);
//                Log.d("Msg", " " + data_api.getMsg());
//                List<Comics> list_comic = data_api.getData();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        // cập nhật giao diện
//                        comicsAdapter = new ComicsAdapter(getActivity(),list_comic);
//                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
//                        rcvComic.setLayoutManager(gridLayoutManager);
//                        rcvComic.setAdapter(comicsAdapter);
//                    }
//                });
//
//            }
//        });
//
//
//    }