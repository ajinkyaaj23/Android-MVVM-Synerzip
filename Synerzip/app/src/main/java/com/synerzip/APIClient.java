package com.synerzip;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIClient {

    String BASE_URL = "https://itunes.apple.com/us/rss/newfreeapplications/limit=2/"; //https://jsonplaceholder.typicode.com/

    @GET("json")
    Call<List<Feed>> callGetEntities();

}
