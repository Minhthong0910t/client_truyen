package com.example.qltruyen_thonghmph25148.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qltruyen_thonghmph25148.MainActivity;
import com.example.qltruyen_thonghmph25148.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvExit;
    private TextInputEditText edUsername ;
    private TextInputEditText edName;
    private TextInputEditText edEmail;
    private TextInputEditText edPassword;
    private TextInputEditText edRePassword;
    private Button btnSignup;

    private Socket mSocket;
    {
        try {
            mSocket= IO.socket("http://10.0.2.2:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        anhXa();
    }

    private void anhXa(){
        //mở kết nối
        mSocket.connect();
        //lắng nghe sự kiện

        mSocket.on("new msg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                SignUpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data_sv_send= (String) args[0];
                        Toast.makeText(SignUpActivity.this, "Thông báo mới nhất "+data_sv_send, Toast.LENGTH_SHORT).show();

                        //hiện notify lên status
                        postNotify("Bạn Đã Đăng Ký Thành Công!",data_sv_send);

                    }

                });
            }
        });
        tvExit = findViewById(R.id.tv_exit);
        edUsername = findViewById(R.id.ed_Username);
        edName = findViewById(R.id.ed_Name);
        edEmail = findViewById(R.id.ed_Email);
        edPassword = findViewById(R.id.ed_sPassword);
        edRePassword = findViewById(R.id.ed_RePassword);
        btnSignup = findViewById(R.id.btn_Signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register("http://10.0.2.2:3000/api/users/register");

            }
        });
        tvExit.setOnClickListener(view -> {
            finish();
        });
    }

    private void register(String link) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String urlLink = link;

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlLink);
                    // Mã kết nối
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    // Thiết lập phương thức POST , mặc định sẽ là GET
                    http.setRequestMethod("POST");

                    // Kiểm tra điều kiện nhập
                    String username = edUsername.getText().toString();
                    String fullname = edName.getText().toString();
                    String email = edEmail.getText().toString();
                    String password = edPassword.getText().toString().trim();
                    String checkpwd= edRePassword.getText().toString().trim();

                    Log.d("Gggggg", "run: "+password);
                    Log.d("Gggggg222222", "run: "+checkpwd);

                    // Kiểm tra nhập trống
                    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullname.isEmpty()|| checkpwd.isEmpty()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if(!checkpwd.equals(password)){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, "mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    // Kiểm tra username có ít nhất 6 kí tự
                    if (username.length() < 6) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, "Username phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    // Kiểm tra password có ít nhất 6 kí tự, 1 chữ hoa và 1 kí tự đặc biệt
                    if (password.length() < 6 || !password.matches(".*[A-Z].*") || !password.matches(".*[@#$%^&+=].*")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, "Password phải có ít nhất 6 kí tự, 1 chữ hoa và 1 kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    // Kiểm tra định dạng email
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!email.matches(emailPattern)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    // Tiếp tục xử lý đăng kí
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userName", username);
                    jsonObject.put("password", password);
                    jsonObject.put("email", email);
                    jsonObject.put("fullname", fullname);

                    http.setRequestProperty("Content-Type", "application/json");
                    // Tạo đối tượng out dữ liệu ra khỏi ứng dụng để gửi lên server
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.append(jsonObject.toString());
                    // Xóa bộ đệm
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    // Nhận lại dữ liệu phản hồi
                    InputStream inputStream = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String dong; // dòng dữ liệu đọc được
                    // đọc dữ liệu
                    while ((dong = reader.readLine()) != null) {
                        builder.append(dong).append("\n");
                    }
                    // kết thúc quá trình đọc:
                    reader.close();
                    inputStream.close();
                    http.disconnect();


                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        mSocket.emit("new msg","Đăng kí thành công");
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(SignUpActivity.this, NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(SignUpActivity.this);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(SignUpActivity.this,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(SignUpActivity.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }

}