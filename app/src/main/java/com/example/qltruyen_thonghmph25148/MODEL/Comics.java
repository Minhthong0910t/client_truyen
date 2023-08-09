package com.example.qltruyen_thonghmph25148.MODEL;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Comics implements Parcelable {
    private String _id;
    private String name;
    private String disc;
    private String tacgia;
    private String date;
    private String img;
    private String content;

    public Comics(String _id, String name, String disc, String tacgia, String date, String img, String content) {
        this._id = _id;
        this.name = name;
        this.disc = disc;
        this.tacgia = tacgia;
        this.date = date;
        this.img = img;
        this.content = content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected Comics(Parcel in) {
        _id = in.readString();
        name = in.readString();
        disc = in.readString();
        tacgia = in.readString();
        date = in.readString();
        img = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeString(_id);
        parcel.writeString(name);
        parcel.writeString(disc);
        parcel.writeString(tacgia);
        parcel.writeString(date);
        parcel.writeString(img);
        parcel.writeString(content);
    }


    public static final Creator<Comics> CREATOR = new Creator<Comics>() {
        @Override
        public Comics createFromParcel(Parcel in) {
            return new Comics(in);
        }

        @Override
        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };
}
