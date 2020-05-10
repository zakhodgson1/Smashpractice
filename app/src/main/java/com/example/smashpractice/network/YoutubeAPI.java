package com.example.smashpractice.network;

import com.example.smashpractice.models.ModelHome;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPI {

    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String KEY = "key=AIzaSyDCmrfsUUfTHcUmBwiPAF8RUx_H90ie2ZQ";
    public static final String sch = "search?";
    public static final String mx = "&maxResults=10";
    public static final String ord = "&order=date";
    public static final String part = "&part=snippet";

    public static final String query = "&q=";
    public static final String type = "&type=video";

    public interface HomeVideo {
        @GET
        Call<ModelHome> getYT(@Url String url);
    }

    private static HomeVideo homeVideo = null;

    public static HomeVideo getHomeVideo() {
        if (homeVideo == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            homeVideo = retrofit.create(HomeVideo.class);
        }
        return homeVideo;
    }
}
