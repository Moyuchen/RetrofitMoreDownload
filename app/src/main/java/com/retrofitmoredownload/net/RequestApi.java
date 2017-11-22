package com.retrofitmoredownload.net;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by yfeng on 2017/11/16.
 */

public interface RequestApi {
    public static final String BASE_URL = "http://mirror.aarnet.edu.au";
    public static final String BASE_URL2 = "http://2449.vod.myqcloud.com";




    @Streaming
    @POST("/2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4")
    Observable<ResponseBody> downloadFile(@Header("Range") String range);

    @Streaming
    @POST("/2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4")
    Call<ResponseBody> getFileLenght();
}
