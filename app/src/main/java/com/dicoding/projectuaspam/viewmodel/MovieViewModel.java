package com.dicoding.projectuaspam.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.projectuaspam.BuildConfig;
import com.dicoding.projectuaspam.model.ResponseMovie;
import com.dicoding.projectuaspam.model.ResultsItem;
import com.dicoding.projectuaspam.network.ApiConfig;
import com.dicoding.projectuaspam.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private static String TAG = MovieViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<ResultsItem>> listMovies;
    private ApiService apiService;

    private static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public LiveData<ArrayList<ResultsItem>> getMovies(Context context) {
        if (listMovies == null) {
            listMovies = new MutableLiveData<ArrayList<ResultsItem>>();
            apiService = ApiConfig.getInstance().create(ApiService.class);
            moviesList(context);
        }
        return listMovies;
    }

    public void getQueryMovies(Context context, String query) {
        searchMovie(context, query);
    }

    public void moviesList(final Context context) {
        apiService.getListMovies(BuildConfig.TMDB_API_KEY).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                ResponseMovie responseMovies = response.body();
                if (responseMovies != null) {
                    List<ResultsItem> resultsItems = responseMovies.getResults();

                    listMovies.postValue((ArrayList<ResultsItem>) resultsItems);
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                showMessage(context, t.getMessage());
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void searchMovie(final Context context, String query) {

        apiService.getQueryListMovies(BuildConfig.TMDB_API_KEY, query).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                ResponseMovie responseMovie = response.body();
                if (responseMovie != null) {
                    List<ResultsItem> resultsItems = response.body().getResults();

                    listMovies.postValue((ArrayList<ResultsItem>) resultsItems);
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                showMessage(context, t.getMessage());
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<ResultsItem>> getListMovies() {
        return listMovies;
    }
}
