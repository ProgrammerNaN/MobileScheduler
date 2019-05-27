package com.example.teleg.programm.serverSide.Api;

import com.example.teleg.programm.serverSide.News.Feed;
import com.example.teleg.programm.serverSide.Schedule.PostModel;
import com.example.teleg.programm.serverSide.Schedule.Settings;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RsreuApi {
    @GET("component/ninjarsssyndicator/?feed_id=1&format=raw")
    Observable<Feed> getItems();

    @GET("schedule/{group}.json")
    Observable<PostModel> getRasp(@Path("group") String group);

    @GET("schedule/settings.json")
    Observable<Settings> getSettings();
}
