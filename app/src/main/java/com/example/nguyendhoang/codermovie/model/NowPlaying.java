package com.example.nguyendhoang.codermovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/11/2016.
 */
public class NowPlaying {

    @SerializedName("results")
    private List<Movie> moives;


    public List<Movie> getMoives() {
        return moives;
    }
}
