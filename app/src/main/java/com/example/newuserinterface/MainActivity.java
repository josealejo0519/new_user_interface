package com.example.newuserinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnApps, btnCategorias, btnMenu, btnLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnApps = findViewById(R.id.btnApps);
        btnCategorias = findViewById(R.id.btnCategorias);
        btnMenu = findViewById(R.id.btnMenu);
        btnLogout = findViewById(R.id.btnLogout); // Botón de cerrar sesión

        btnApps.setOnClickListener(v -> openMenuActivity("apps"));
        btnCategorias.setOnClickListener(v -> openMenuActivity("categorias"));
        btnMenu.setOnClickListener(v -> openMenuActivity("menu"));

        btnLogout.setOnClickListener(v -> cerrarSesion()); // Acción de cerrar sesión
    }

    private void openMenuActivity(String value) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("extra", value);
        startActivity(intent);
    }

    private void cerrarSesion() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
