package com.example.qltruyen_thonghmph25148.MODEL;

public class Comment {
    private String _id;
    private String id_comic;
    private String id_user;
    private String contents;
    private String timecm;

    public Comment(String _id, String id_comic, String id_user, String contents, String timecm) {
        this._id = _id;
        this.id_comic = id_comic;
        this.id_user = id_user;
        this.contents = contents;
        this.timecm = timecm;
    }

    public Comment() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_comic() {
        return id_comic;
    }

    public void setId_comic(String id_comic) {
        this.id_comic = id_comic;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTimecm() {
        return timecm;
    }

    public void setTimecm(String timecm) {
        this.timecm = timecm;
    }
}
