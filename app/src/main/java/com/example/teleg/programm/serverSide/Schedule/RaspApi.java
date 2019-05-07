package com.example.teleg.programm.serverSide.Schedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RaspApi {
    @GET("schedule/{group}.json")
    Call<PostModel> getRasp(@Path("group") String group);

}
