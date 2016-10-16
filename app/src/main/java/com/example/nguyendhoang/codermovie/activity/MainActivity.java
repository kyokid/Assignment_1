package com.example.nguyendhoang.codermovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nguyendhoang.codermovie.R;
import com.example.nguyendhoang.codermovie.adapter.MovieAdapter;
import com.example.nguyendhoang.codermovie.api.MovieApi;
import com.example.nguyendhoang.codermovie.model.Movie;
import com.example.nguyendhoang.codermovie.model.NowPlaying;
import com.example.nguyendhoang.codermovie.utils.Constant;
import com.example.nguyendhoang.codermovie.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.lvMovie)
    ListView lvMovie;

    private MovieApi mMovieApi;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private List<String> UrlTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupSwipeContainer();

        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);


        if (null == savedInstanceState) {
            fetchMovies();
        } else {
            handleRotateOrientation(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<Movie> list = (ArrayList<Movie>) ((MovieAdapter) lvMovie.getAdapter()).getmMovie();
        outState.putParcelableArrayList(Constant.LIST_DATA_MOVIE, list);
        super.onSaveInstanceState(outState);
    }


    private void setupSwipeContainer() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieApi.getPopular().enqueue(new Callback<NowPlaying>() {
                    @Override
                    public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                        Log.d("Reponses", String.valueOf(response.isSuccessful()));

                        // clear old data of adapter
                        lvMovie.setAdapter(null);
                        // add new data from response to adapter
                        lvMovie.setAdapter(new MovieAdapter(MainActivity.this, response.body().getMoives()));

                        // Now we call setRefreshing(false) to signal refresh has finished
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<NowPlaying> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });

        swipeContainer.setColorSchemeResources(R.color.holoBlueBright,
                R.color.holoGreenLight,
                R.color.holoOrangeLight,
                R.color.holoRedLight);
    }

    private void fetchMovies() {
        String url = mMovieApi.getPopular().request().url().toString();
        Log.d("Reponse Url movie", url);
        mMovieApi.getPopular().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Log.d("Reponse", String.valueOf(response.isSuccessful()));
                Log.d("Reponse ", call.request().url().toString());
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    private void handleResponse(Response<NowPlaying> response) {
        lvMovie.setAdapter(new MovieAdapter(MainActivity.this, response.body().getMoives()));
        pbLoading.setVisibility(View.GONE);
    }

    private void handleRotateOrientation(Bundle savedInstanceState) {
        ArrayList<Movie> listSaved = savedInstanceState.getParcelableArrayList(Constant.LIST_DATA_MOVIE);
        // clear old data of adapter
        lvMovie.setAdapter(null);
        // add new data from response to adapter
        lvMovie.setAdapter(new MovieAdapter(MainActivity.this, listSaved));
        pbLoading.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK && requestCode == Constant.MOVIE_DETAIL_REQUEST_CODE) {
            Toast.makeText(MainActivity.this, "Welcome back", Toast.LENGTH_SHORT);
        }
    }
}
