package com.ooxx.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by wengzhch on 2016/4/11.
 */
public interface BaiduService {

    @GET("/")
    Call<String> getMainPage();
}
