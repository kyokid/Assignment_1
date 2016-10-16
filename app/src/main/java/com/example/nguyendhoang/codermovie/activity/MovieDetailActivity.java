package com.example.nguyendhoang.codermovie.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nguyendhoang.codermovie.R;
import com.example.nguyendhoang.codermovie.api.TrailerApi;
import com.example.nguyendhoang.codermovie.model.Movie;
import com.example.nguyendhoang.codermovie.model.Trailer;
import com.example.nguyendhoang.codermovie.model.TrailerYoutube;
import com.example.nguyendhoang.codermovie.utils.Constant;
import com.example.nguyendhoang.codermovie.utils.RetrofitUtils;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends YouTubeBaseActivity {
    Movie mMovie;

    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvPopularity)
    TextView tvPopularity;

    @BindView(R.id.ypMovie)
    YouTubePlayerView ypMovie;

    @BindView(R.id.rbMovie)
    RatingBar rbMovie;

    private TrailerApi mTrailerApi;

    private List<Trailer> mListTrailer;

    private YouTubePlayer mYoutubePlayer;

    private boolean mFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        // Get movie from MainActivity
        handleGetArgs(savedInstanceState);

        mTrailerApi = RetrofitUtils.get(getString(R.string.api_key)).create(TrailerApi.class);

        // get url trailer by movie id
        getUrlTrailer();


        // Init View
        initView();


    }

    private void handleGetArgs(Bundle bundle) {
        mMovie = getIntent().getParcelableExtra(Constant.MOVIE_DETAIL);
    }

    private void initView() {
        tvTitle.setText(mMovie.getTitle());
        tvReleaseDate.setText(Constant.RELEASE_DATE + mMovie.getReleaseDate());
        tvOverview.setText(mMovie.getOverview());
        tvPopularity.setText(String.valueOf(mMovie.getPopularity()) + Constant.POPULARITY);
        rbMovie.setRating((float) mMovie.getVoteAverage() / 2);
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);

        super.onDestroy();
    }

    private void initYtMovie() {
        ypMovie.initialize(getString(R.string.api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        mYoutubePlayer = youTubePlayer;
                        Trailer trailer = mListTrailer.get(0);
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(trailer.getSource());
                        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                            @Override
                            public void onFullscreen(boolean b) {
                                mFullScreen = b;
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    private void getUrlTrailer() {
        mTrailerApi.getTrailer(mMovie.getId().toString()).enqueue(new Callback<TrailerYoutube>() {
            @Override
            public void onResponse(Call<TrailerYoutube> call, Response<TrailerYoutube> response) {
                Log.d("Reponse url youtube: ", String.valueOf(response.isSuccessful()));
                Log.d("Reponse movie detail", call.request().url().toString());
                hanleResponse(response);
            }

            @Override
            public void onFailure(Call<TrailerYoutube> call, Throwable t) {
                Log.d("Failure url youtube: ", String.valueOf(t.getMessage()));
            }
        });

    }

    private void hanleResponse(Response<TrailerYoutube> response) {
        mListTrailer = response.body().getTrailers();
        initYtMovie();
    }

    @Override
    public void onBackPressed() {
        if (mFullScreen) {
            mYoutubePlayer.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle bundle) {
//        int currentPositionVideo=mYoutubeplayer.getCurrentTimeMillis();
//        bundle.putInt("Video postion", currentPositionVideo);
//        mYoutubeplayer.pause();
//        super.onSaveInstanceState(bundle);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        int currentPositionVideo=savedInstanceState.getInt("Video postion");
//        initYtMovie();
//        mYoutubeplayer.seekToMillis(currentPositionVideo);
//        mYoutubeplayer.play();
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}
