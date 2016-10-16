package com.example.nguyendhoang.codermovie.api;

import com.example.nguyendhoang.codermovie.model.NowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Nguyen.D.Hoang on 10/11/2016.
 */
public interface MovieApi {

    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();

    @GET("popular")
    Call<NowPlaying> getPopular();

}
