package com.lucas.locationfavorite.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.lucas.locationfavorite.Controller.DBController;
import com.lucas.locationfavorite.Model.User;
import com.lucas.locationfavorite.R;

public class CreateActivity extends AppCompatActivity {

    private TextInputEditText et_name, et_email, et_password, et_confirmPassword;
    private Button btnCreate;
    private DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.createaccount_activity);

        dbController = new DBController(this);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        btnCreate = findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> {
            if (et_name.getText().toString().trim().isEmpty() || et_password.getText().toString().trim().isEmpty() || et_email.getText().toString().trim().isEmpty() || et_confirmPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_please_fill_in_all_fields), Toast.LENGTH_SHORT).show();
                return;
            } else if (et_name.getText().toString().trim().length() < 3 && et_name.getText().toString().trim().length() > 12) {
                Toast.makeText(this, getString(R.string.msg_error_User), Toast.LENGTH_SHORT).show();
                return;
            } else if (et_password.getText().toString().trim().length() < 8 && et_password.getText().toString().trim() != et_confirmPassword.getText().toString().trim()) {
                Toast.makeText(this, getString(R.string.msg_error_Password), Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(et_name.getText().toString(), et_password.getText().toString(), et_email.getText().toString());
            dbController.insertData(user.getFullname(), user.getPassword(), user.getEmail());

            Toast.makeText(this, getString(R.string.msg_savedData), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CreateActivity.this, LocationFavoriteActivity.class);
            startActivity(intent);
        });
    }
}
