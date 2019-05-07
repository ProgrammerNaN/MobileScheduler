package com.example.teleg.programm.serverSide.News;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ControllerNews implements Callback<Feed> {

    static final String BASE_URL = "http://www.rsreu.ru/";

    public void start() {
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BASE_URL)
                                        .addConverterFactory(SimpleXmlConverterFactory.create())
                                        .build();
        RssAdapter rssAdapter = retrofit.create(RssAdapter.class);

        Call<Feed> call = rssAdapter.getItems();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<Feed> call, Response<Feed> response) {
        if (response.isSuccessful()) {
            Feed feed = response.body();
            List<ItemNews>  news = feed.getChannel().getNewsList();
            RVAdapter adapter = new RVAdapter(news);

        }
    }

    @Override
    public void onFailure(Call<Feed> call, Throwable t) {
        t.printStackTrace();
    }
}
