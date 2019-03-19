package com.nytimes.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nytimes.assignment.R;


public class SplashActivity extends AppCompatActivity {

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            GoToMainActivity();
        }
    };
    private Handler handler = new Handler();
    private final int DELAY_TIME = 2000; // In milli seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null) {
            handler.postDelayed(runnable, DELAY_TIME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void GoToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }
}
