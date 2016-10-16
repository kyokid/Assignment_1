package com.example.nguyendhoang.codermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyendhoang.codermovie.R;
import com.example.nguyendhoang.codermovie.activity.MainActivity;
import com.example.nguyendhoang.codermovie.activity.MovieDetailActivity;
import com.example.nguyendhoang.codermovie.model.Movie;
import com.example.nguyendhoang.codermovie.utils.Constant;
import com.example.nguyendhoang.codermovie.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nguyen.D.Hoang on 10/11/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> mMovie;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, -1);
        mMovie = movies;
    }

    @Override
    public int getCount() {
        return mMovie.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getTypeMovie(mMovie.get(position));
    }

    @Override
    public int getViewTypeCount() {
        return Movie.MovieType.values().length;
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return mMovie.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ViewHolderFiveStart viewHolderFiveStart = null;
        Movie movie = getItem(position);
        int type = getTypeMovie(movie);
        if (convertView == null) {

            convertView = getInflatedLayoutForType(type);

            if (type == Constant.MOVIELESSFIVESTART) {
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolderFiveStart = new ViewHolderFiveStart(convertView);
                convertView.setTag(viewHolderFiveStart);
            }

        } else {
            if (type == Constant.MOVIELESSFIVESTART) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                viewHolderFiveStart = (ViewHolderFiveStart) convertView.getTag();
            }

        }

        if (type == Constant.MOVIELESSFIVESTART) {
            bindViewMovie(movie, viewHolder);
        } else {
            bindViewMovie(movie, viewHolderFiveStart);
        }

        //Fill the data


        return convertView;
    }

    private void bindViewMovie(final Movie movie, ViewHolder viewHolder) {
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        Configuration configuration = getContext().getResources()
                .getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Utils.loadImage(viewHolder.ivCover, movie.getPosterPath(), getContext(), Constant.ORIENTATION_PORTRAIT);
        } else {
            Utils.loadImage(viewHolder.ivCover, movie.getBackdropPath(), getContext(), Constant.ORIENTATION_LANDSCAPE);
        }

        viewHolder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(Constant.MOVIE_DETAIL, movie);
                intent.putExtra(Constant.MOVIE_TYPE, Constant.MOVIELESSFIVESTART);

                ((MainActivity) getContext()).startActivityForResult(intent, Constant.MOVIE_DETAIL_REQUEST_CODE);
            }
        });
    }

    private void bindViewMovie(final Movie movie, ViewHolderFiveStart viewHolderFiveStart) {

        Utils.loadImage(viewHolderFiveStart.ivCover, movie.getBackdropPath(), getContext(), Constant.ORIENTATION_LANDSCAPE);


        viewHolderFiveStart.ivVideoPreviewPlayButton.setVisibility(View.VISIBLE);
        viewHolderFiveStart.ivVideoPreviewPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(Constant.MOVIE_DETAIL, movie);
                intent.putExtra(Constant.MOVIE_TYPE, Constant.MOVIEGREATFIVESTART);

                ((MainActivity) getContext()).startActivityForResult(intent, Constant.MOVIE_DETAIL_REQUEST_CODE);
            }
        });
    }

    class ViewHolder {
        @BindView(R.id.tvTitle)
        public TextView tvTitle;

        @BindView(R.id.tvOverview)
        public TextView tvOverview;

        @BindView(R.id.ivCover)
        public ImageView ivCover;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderFiveStart {

        @BindView(R.id.ivCover)
        public ImageView ivCover;

        @BindView(R.id.ivVideoPreviewPlayButton)
        public ImageView ivVideoPreviewPlayButton;

        public ViewHolderFiveStart(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public List<Movie> getmMovie() {
        return mMovie;
    }

    // vote_average < 5: type = 1
    // vote_average > 5 : type = 2
    private int getTypeMovie(Movie movie) {
        int type = Constant.MOVIELESSFIVESTART;
        if (movie.getVoteAverage() > 5) {
            type = Constant.MOVIEGREATFIVESTART;
        }
        return type;
    }

    private View getInflatedLayoutForType(int type) {
        if (type == Constant.MOVIELESSFIVESTART) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else if (type == Constant.MOVIEGREATFIVESTART) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_five_start, null);
        } else {
            return null;
        }
    }
}
