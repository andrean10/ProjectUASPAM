package com.dicoding.projectuaspam.network;

import com.dicoding.projectuaspam.model.ResponseMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie")
    Call<ResponseMovie> getListMovies(
            @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<ResponseMovie> getQueryListMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );
}