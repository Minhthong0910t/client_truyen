package com.example.qltruyen_thonghmph25148.MODEL.LIST_API;

import com.example.qltruyen_thonghmph25148.MODEL.Comics;

import java.util.List;

public class DataComics_api {

    private List<Comics> data;
    private String msg;
    private boolean success;

    public DataComics_api() {
    }

    public DataComics_api(List<Comics> data, String msg, boolean success) {
        this.data = data;
        this.msg = msg;
        this.success = success;
    }

    public List<Comics> getData() {
        return data;
    }

    public void setData(List<Comics> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
