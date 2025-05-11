package com.example.newuserinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnEntrada, btnSalida, btnCelda, btnPago, btnObs, btnReporte, btnLogout;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a botones
        btnEntrada = findViewById(R.id.btnEntrada);
        btnSalida = findViewById(R.id.btnSalida);
        btnCelda = findViewById(R.id.btnCelda);
        btnPago = findViewById(R.id.btnPago);
        btnObs = findViewById(R.id.btnObs);
        btnReporte = findViewById(R.id.btnReporte);
        btnLogout = findViewById(R.id.btnLogout);

        recyclerView = findViewById(R.id.recyclerViewIngresos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Acciones de botones
        btnEntrada.setOnClickListener(v -> openMenuActivity("entrada"));
        btnSalida.setOnClickListener(v -> openMenuActivity("salida"));
        btnCelda.setOnClickListener(v -> openMenuActivity("celda"));
        btnPago.setOnClickListener(v -> openMenuActivity("pago"));
        btnObs.setOnClickListener(v -> openMenuActivity("obs"));
        btnReporte.setOnClickListener(v -> openMenuActivity("reporte"));
        btnLogout.setOnClickListener(v -> cerrarSesion());

        cargarVehiculos();
    }

    private void cargarVehiculos() {
        List<AppModel> listaApp = new ArrayList<>();

        try {
            List<Vehiculo> vehiculos = Vehiculo.findWithQuery(
                    Vehiculo.class,
                    "SELECT * FROM Vehiculo WHERE fecha_salida = 0 ORDER BY fecha_ingreso DESC LIMIT 10"
            );

            // Si la tabla aún no existe o está vacía, insertamos un registro de prueba
            if (vehiculos == null || vehiculos.isEmpty()) {
                Vehiculo vehiculoPrueba = new Vehiculo("TEST123", "Automóvil", "Z1", System.currentTimeMillis());
                vehiculoPrueba.save();

                Toast.makeText(this, "Vehículo de prueba agregado", Toast.LENGTH_SHORT).show();

                vehiculos = Vehiculo.findWithQuery(
                        Vehiculo.class,
                        "SELECT * FROM Vehiculo WHERE fecha_salida = 0 ORDER BY fecha_ingreso DESC LIMIT 10"
                );
            }

            for (Vehiculo v : vehiculos) {
                if (v.getFechaIngreso() > 0) {
                    String fechaFormateada = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
                            .format(new java.util.Date(v.getFechaIngreso()));

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
            case "pago":
                intent = new Intent(MainActivity.this, RegistroPagoActivity.class);
                break;
            case "obs":
                intent = new Intent(MainActivity.this, RegistroObservacionActivity.class);
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
