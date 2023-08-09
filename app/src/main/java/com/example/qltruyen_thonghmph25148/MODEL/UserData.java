package com.example.qltruyen_thonghmph25148.MODEL;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("_id")
    private String _id;
    public String toString(){
        return " "+_id + ""+userName + " "+password;
    }


    public UserData(String userName, String password, String _id) {
        this.userName = userName;
        this.password = password;
        this._id = _id;
    }

    protected UserData(Parcel in) {
        userName = in.readString();
        password = in.readString();
        _id = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(_id);
    }
}
