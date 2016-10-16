package com.example.nguyendhoang.codermovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/13/2016.
 */

public class TrailerYoutube {

    @SerializedName("youtube")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
