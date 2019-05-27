package com.example.teleg.programm.serverSide.Api;

import com.example.teleg.programm.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class ClientJson {
    private static ClientJson ourInstance = new ClientJson();
    private static RsreuApi api;

    private static final String BASE_URL = "http://www.rsreu.ru/";

    public static ClientJson getInstance(){
        return ourInstance;
    }

    private ClientJson() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(RsreuApi.class);

    }

    public RsreuApi getApi() {
        return api;
    }
}
