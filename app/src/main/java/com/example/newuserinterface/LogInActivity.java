package com.example.newuserinterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameEdT);
        password = findViewById(R.id.passwordEdT);
        Button btnSingIn = findViewById(R.id.btnSingIn);
        Button btnSingUp = findViewById(R.id.btnSingUp);

        btnSingIn.setOnClickListener(v -> processLoginData());

        btnSingUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, FormActivity.class);
            startActivity(intent);
        });
    }

    private void processLoginData() {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<User> users = User.find(User.class, "email = ?", user);

        if (users.isEmpty()) {
            Toast.makeText(this, "Email not registered. Please sign up.", Toast.LENGTH_SHORT).show();
        } else {
            User foundUser = users.get(0);
            if (foundUser.getPassword().equals(pass)) {
                Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();


                // Guardar sesión
                SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("is_logged_in", true);
                editor.apply();

                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

