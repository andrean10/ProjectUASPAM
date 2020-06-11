package com.dicoding.projectuaspam.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dicoding.projectuaspam.R;
import com.dicoding.projectuaspam.adapter.MovieAdapter;
import com.dicoding.projectuaspam.databinding.ActivityHomeBinding;
import com.dicoding.projectuaspam.model.ResultsItem;
import com.dicoding.projectuaspam.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String EXTRA_STATE = "extra_state";
    private ActivityHomeBinding binding;
    private MovieAdapter adapter = new MovieAdapter(HomeActivity.this);
    private MovieViewModel movieViewModel;
    private ArrayList<ResultsItem> saveResultItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);
        showLoading(true);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        if (savedInstanceState == null) {
            movieViewModel.getMovies(this);
        } else {
            saveResultItems = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (saveResultItems != null) {
                adapter.setData(saveResultItems);
            }
        }

        movieViewModel.getMovies(this).observe(this, new Observer<ArrayList<ResultsItem>>() {
            @Override
            public void onChanged(ArrayList<ResultsItem> resultsItems) {
                if (resultsItems != null) {
                    adapter.setData(resultsItems);
                    binding.rv.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    binding.rv.setAdapter(adapter);
                    showLoading(false);
                } else {
                    showLoading(false);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.input_search));
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            movieViewModel.moviesList(this);
        } else {
            movieViewModel.getQueryMovies(this, newText);
        }
        showLoading(true);
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getData());
    }
}