package com.lucas.locationfavorite.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.locationfavorite.R;

public class SplashActivity extends AppCompatActivity {

    public static final int TIME_OUT_SPLASH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash_activity);

        comutartelaSplash();
    }
    private void comutartelaSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainScreen = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(mainScreen);
                finish();
            }
        },TIME_OUT_SPLASH);
    }
}