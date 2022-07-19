package com.example.samplesocial.Retrofit;

import com.example.samplesocial.UtilityTools.Const;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Rakhi 12/04/2021.
 */

public class ApiClient {
    public static final String BASE_URL = Const.URL.HOST_URL;
    public static final String BASE_URLImage = Const.URL.IMAGE_URL;
    public static final String REGISTRATION_COMPLETE = "registration_complete";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static Retrofit retrofit3 = null;


    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientFaceBook() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build();


        retrofit2 = new Retrofit.Builder()
                .baseUrl(Constant.facebook_post)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit2;
    }

    public static Retrofit getClintInstagram() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build();


        retrofit3 = new Retrofit.Builder()
                .baseUrl(Const.URL.instaFeedBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit3;
    }


}
