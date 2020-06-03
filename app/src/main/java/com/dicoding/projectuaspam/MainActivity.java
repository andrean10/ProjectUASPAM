package com.dicoding.projectuaspam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.projectuaspam.databinding.ActivityMainBinding;
import com.dicoding.projectuaspam.view.HomeActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int time_load = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, time_load);
    }
}