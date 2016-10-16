package com.example.nguyendhoang.codermovie.utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nguyen.D.Hoang on 10/11/2016.
 */
public class RetrofitUtils {

    public static Retrofit get(String apiKey){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client(apiKey))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private static OkHttpClient client(String apiKey){
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor(apiKey))
                .build();
    }

    private static Interceptor apiKeyInterceptor(final String apiKey){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build();
                request = request.newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);

            }
        };
    }
}
