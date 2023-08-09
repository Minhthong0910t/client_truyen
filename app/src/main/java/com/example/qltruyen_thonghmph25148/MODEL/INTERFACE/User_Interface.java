package com.example.qltruyen_thonghmph25148.MODEL.INTERFACE;

import com.example.qltruyen_thonghmph25148.MODEL.LIST_API.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface User_Interface {

    @GET("users")
    Call<List<User>> lay_danh_sach();

    //Đăng Ký user
    @POST("users")
    Call<User> register_user(@Body User objU);
    //Đăng Ký user
    @POST("users")
    Call<User> login_user(@Body User objU);
}
