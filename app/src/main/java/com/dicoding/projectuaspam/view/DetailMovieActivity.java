package com.dicoding.projectuaspam.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dicoding.projectuaspam.R;
import com.dicoding.projectuaspam.databinding.ActivityDetailMovieBinding;
import com.dicoding.projectuaspam.model.ResultsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "extra_name";
    private static final String URL_IMG = "https://image.tmdb.org/t/p/w500";
    private ActivityDetailMovieBinding binding;
    private ResultsItem databaseIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailMovieBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        binding.floatBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.floatBtnBack) {
                    finish();
                }
            }
        });
        showLoadingImage(true);
        showLoadingContent(true);

        databaseIntent = getIntent().getParcelableExtra(EXTRA_NAME);
        if (databaseIntent != null) {
            prepare(databaseIntent);
        } else {
            prepare(null);
        }
    }

    void prepare(ResultsItem resultsItem) {
        if (resultsItem != null) {
            setDetailTitle(databaseIntent.getTitle());

            Glide.with(this)
                    .load(URL_IMG + databaseIntent.getPosterPath())
                    .placeholder(R.drawable.img_not_found)
                    .error(R.drawable.img_not_found)
                    .into(binding.expandedImage);

            binding.tvRelease.setText(convertToDatePattern(resultsItem.getReleaseDate()));
            binding.tvPopularity.setText(String.valueOf(resultsItem.getPopularity()));
            binding.tvRating.setText(String.valueOf(resultsItem.getVoteAverage()));
            binding.rbVoteAverage.setRating((float) (resultsItem.getVoteAverage() / 2));
            binding.tvDescription.setText(resultsItem.getOverview());
            showLoadingContent(false);
            showLoadingImage(false);
        } else {
            showLoadingImage(true);
            showLoadingContent(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String convertToDatePattern(String release) {
        SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat toString = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date date;
        String setDate = "";
        try {
            date = toDate.parse(release);
            assert date != null;
            setDate = toString.format(date);
            return setDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return setDate;
    }

    private void setDetailTitle(String dataTitle) {
        requireNonNull(getSupportActionBar()).setTitle(dataTitle);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void showLoadingImage(boolean state) {
        if (state) {
            binding.progressBarImage.setVisibility(View.VISIBLE);
        } else {
            binding.progressBarImage.setVisibility(View.GONE);
        }
    }

    private void showLoadingContent(boolean state) {
        if (state) {
            binding.progressBarContent.setVisibility(View.VISIBLE);
        } else {
            binding.progressBarContent.setVisibility(View.GONE);
        }
    }
}
