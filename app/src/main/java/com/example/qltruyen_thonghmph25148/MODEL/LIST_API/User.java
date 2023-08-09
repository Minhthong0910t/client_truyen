package com.example.qltruyen_thonghmph25148.MODEL.LIST_API;

public class User {
    private String _id;
    private String userName;
    private String password;
    private String email;
    private String fullname;

    public User(String _id, String userName, String password, String email, String fullname) {
        this._id = _id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
    }

    public User() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
