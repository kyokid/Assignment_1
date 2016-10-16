package com.example.nguyendhoang.codermovie.api;

import com.example.nguyendhoang.codermovie.model.TrailerYoutube;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Nguyen.D.Hoang on 10/13/2016.
 */

public interface TrailerApi {

    @GET("{id}/trailers")
    Call<TrailerYoutube> getTrailer(@Path(value = "id", encoded = false) String idMovie);
}
