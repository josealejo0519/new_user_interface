package com.example.newuserinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Button btnEntrada, btnSalida, btnCelda, btnCliente, btnReporte, btnLogout;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrada = findViewById(R.id.btnEntrada);
        btnSalida = findViewById(R.id.btnSalida);
        btnCelda = findViewById(R.id.btnCelda);
        btnCliente = findViewById(R.id.btnCliente);
        btnReporte = findViewById(R.id.btnReporte);
        btnLogout = findViewById(R.id.btnLogout);

        recyclerView = findViewById(R.id.recyclerViewIngresos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        btnEntrada.setOnClickListener(v -> openMenuActivity("entrada"));
        btnSalida.setOnClickListener(v -> openMenuActivity("salida"));
        btnCelda.setOnClickListener(v -> openMenuActivity("celda"));
        btnCliente.setOnClickListener(v -> openMenuActivity("cliente"));
        btnReporte.setOnClickListener(v -> openMenuActivity("reporte"));
        btnLogout.setOnClickListener(v -> cerrarSesion());

        cargarVehiculos();
    }

    private void cargarVehiculos() {
        List<AppModel> listaApp = new ArrayList<>();

        try {
            List<Vehiculo> vehiculos = Vehiculo.findWithQuery(
                    Vehiculo.class,
                    "SELECT * FROM vehiculo WHERE fecha_salida = 0 ORDER BY fecha_ingreso DESC LIMIT 10"
            );

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            formato.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

            for (Vehiculo v : vehiculos) {
                if (v.getFechaIngreso() > 0) {
                    String fechaFormateada = formato.format(new Date(v.getFechaIngreso()));
                    listaApp.add(new AppModel(
                            v.getPlaca(),
                            v.getTipoVehiculo(),
                            fechaFormateada,
                            v.getCelda()
                    ));
                }
            }

            if (listaApp.isEmpty()) {
                Toast.makeText(this, "No hay vehículos activos", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        AppAdapter adapter = new AppAdapter(this, listaApp);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarVehiculos();
    }

    private void openMenuActivity(String value) {
        Intent intent;

        switch (value) {
            case "entrada":
                intent = new Intent(MainActivity.this, RegistroIngresoActivity.class);
                break;
            case "salida":
                intent = new Intent(MainActivity.this, RegistroSalidaActivity.class);
                break;
            case "celda":
                intent = new Intent(MainActivity.this, RegistroCeldaActivity.class);
                break;
            case "cliente":
                intent = new Intent(MainActivity.this, RegistroClienteActivity.class);
                break;
            case "reporte":
                intent = new Intent(MainActivity.this, ReporteActivity.class);
                break;
            default:
                return;
        }

        startActivity(intent);
    }

    private void cerrarSesion() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", false);
        editor.remove("correo");
        editor.remove("rol");
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

