package com.example.qltruyen_thonghmph25148.MODEL.INTERFACE;

import com.example.qltruyen_thonghmph25148.MODEL.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Comment_Interface {
    @GET("comments") // Thay "comment" bằng đường dẫn API lấy danh sách comment theo id truyện
    Call<List<Comment>> getCommentsByComicId(@Query("id_comic") String id_comic);
    // Định nghĩa phương thức POST để thêm bình luận mới
//    @POST("comments/create")
//    Call<Comment> addComic(@Body Comment objCm);
    @FormUrlEncoded
    @POST("comments/create") // Điền đúng đường dẫn API để thêm bình luận
    Call<Comment> addComment(
            @Field("id_comic") String id_comic,
            @Field("id_user") String id_user,
            @Field("contents") String commentContent,
            @Field("timecm") String timcm
    );

}
