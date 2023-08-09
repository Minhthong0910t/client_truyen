package com.example.qltruyen_thonghmph25148.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qltruyen_thonghmph25148.MODEL.LIST_API.DataUser_api;
import com.example.qltruyen_thonghmph25148.MODEL.UserData;
import com.example.qltruyen_thonghmph25148.MainActivity;
import com.example.qltruyen_thonghmph25148.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

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
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edUsername;
    private TextInputEditText edPassword;
    private Button btn_log;
    private TextView tv_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.ed_Username);
        edPassword = findViewById(R.id.ed_Password);
        btn_log= findViewById(R.id.btn_dnhap);
        tv_signup=findViewById(R.id.tv_signup);

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login("http://10.0.2.2:3000/api/users/login");
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void login(String link){

        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String urlLink = link;

        service.execute(new Runnable() {
            String noidung = "";
            @Override
            public void run() {
                try {
                    URL url = new URL(urlLink);

                    //mã kết nối
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    //THiết lập phương thức POST , mặc định sẽ là GET
                    http.setRequestMethod("POST");

                    String userName = edUsername.getText().toString().trim();
                    String password = edPassword.getText().toString().trim();

                    // Kiểm tra nhập trống
                    if (userName.isEmpty() || password.isEmpty()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    //Tạo đối tượng dữ liệu gửi lên server
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userName",userName);
                    jsonObject.put("password",password);


                    http.setRequestProperty("Content-Type","application/json");
                    //Tạo đối tượng out dữ liệu ra khỏi ứng dụng để gửi lên server
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.append(jsonObject.toString());
                    //Xóa bộ đệm
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //Nhận lại dữ liệu phản hồi

                    int responseCode = http.getResponseCode();
                    if(responseCode==HttpURLConnection.HTTP_OK){
                        InputStream inputStream = http.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String dong; // dòng dữ liệu đọc được
                        // đọc dữ liệu
                        while(  (dong = reader.readLine()) != null  ){
                            builder.append( dong ).append("\n");
                        }
                        // kết thúc quá trình đọc:
                        reader.close();
                        inputStream.close();

                        // Giải mã dữ liệu JSON phản hồi từ server để lấy thông tin người dùng
                        JSONObject responseJson = new JSONObject(builder.toString());
                        String userId = responseJson.optString("userId");
                        String returnedUsername = responseJson.optString("userName");
                        String returnedPassword = responseJson.optString("password");
                        // Tạo đối tượng UserData để truyền sang màn hình Home
                        UserData userData = new UserData(returnedUsername, returnedPassword, userId);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userData", userData);
                                startActivity(intent);
                                finish(); // Đóng màn hình đăng nhập
                            }
                        });

                    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }


        });

    }
}