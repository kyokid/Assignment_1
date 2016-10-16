package com.example.nguyendhoang.codermovie.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.nguyendhoang.codermovie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Nguyen.D.Hoang on 10/13/2016.
 */

public class Utils {

    public static void loadImage(ImageView imageView, String urlPath, Context context, int orientation){
        // orientation = 1
        if(Constant.ORIENTATION_LANDSCAPE == orientation){
            Picasso.with(context)
                    .load(urlPath)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(imageView);
        }else if(Constant.ORIENTATION_PORTRAIT == orientation){
            Picasso.with(context)
                    .load(urlPath)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(imageView);
        }
    }
}
