package com.example.teleg.programm.serverSide.News;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssAdapter {
    @GET("component/ninjarsssyndicator/?feed_id=1&format=raw")
    Call<Feed> getItems();
}
