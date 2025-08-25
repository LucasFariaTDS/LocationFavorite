package com.lucas.locationfavorite.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.locationfavorite.R;

public class MainActivity extends AppCompatActivity {
    private Button btnSingIn, btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnSingIn = findViewById(R.id.btnSignIn);
        btnCreate = findViewById(R.id.btnCreate);

        btnSingIn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });
    }
}