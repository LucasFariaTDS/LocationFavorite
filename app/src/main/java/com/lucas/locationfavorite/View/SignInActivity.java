package com.lucas.locationfavorite.View;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.DateValueSanitizer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.lucas.locationfavorite.DB.DB;
import com.lucas.locationfavorite.R;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText et_email, et_password;
    private TextView tx_create;
    private Button btnSignIn;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signin_activity);

        db = new DB(this);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        tx_create = findViewById(R.id.tx_create);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            String password = et_password.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            if (!password.isEmpty() && !email.isEmpty()) {
                if (db.verifyUser(password, email)) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, LocationFavoriteActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid data!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }

        });
        tx_create.setOnClickListener(v ->{
            Intent intent = new Intent(SignInActivity.this, CreateActivity.class);
            startActivity(intent);
        });
    }
}
