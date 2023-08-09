package com.example.qltruyen_thonghmph25148.MODEL.LIST_API;

import java.util.List;

public class DataUser_api {

    private List<User> data;
    private String msg;
    private boolean success;

    public DataUser_api(List<User> data, String msg, boolean success) {
        this.data = data;
        this.msg = msg;
        this.success = success;
    }

    public DataUser_api() {
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
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
