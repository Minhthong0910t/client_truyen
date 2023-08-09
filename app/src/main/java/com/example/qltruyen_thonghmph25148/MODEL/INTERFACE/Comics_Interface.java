package com.example.qltruyen_thonghmph25148.MODEL.INTERFACE;

import com.example.qltruyen_thonghmph25148.MODEL.Comics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Comics_Interface {

    @GET("comic")
    Call<List<Comics>> danh_sach_comic();
}
