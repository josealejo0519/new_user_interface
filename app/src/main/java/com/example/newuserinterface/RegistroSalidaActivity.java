package com.example.newuserinterface;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RegistroSalidaActivity extends AppCompatActivity {

    private EditText edtPlacaBuscar;
    private Button btnBuscar, btnConfirmarSalida;
    private TextView txtResumen;
    private Vehiculo vehiculoEncontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salida);

        edtPlacaBuscar = findViewById(R.id.edtPlacaBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnConfirmarSalida = findViewById(R.id.btnConfirmarSalida);
        txtResumen = findViewById(R.id.txtResumen);

        btnBuscar.setOnClickListener(v -> buscarVehiculo());

        btnConfirmarSalida.setOnClickListener(v -> {
            if (vehiculoEncontrado != null) {
                long salida = System.currentTimeMillis();
                vehiculoEncontrado.setFechaSalida(salida);
                vehiculoEncontrado.save();

                long tiempoMs = salida - vehiculoEncontrado.getFechaIngreso();
                long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempoMs);

                String fechaSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(salida));

                Toast.makeText(this, "Salida registrada.\nHora: " + fechaSalida +
                        "\nTiempo total: " + minutos + " minutos.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void buscarVehiculo() {
        String placa = edtPlacaBuscar.getText().toString().trim();
        if (placa.isEmpty()) {
            Toast.makeText(this, "Ingresa una placa", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Vehiculo> resultados = Vehiculo.find(Vehiculo.class, "placa = ? AND fecha_salida = 0", placa);

        if (resultados.isEmpty()) {
            txtResumen.setText("Vehículo no encontrado o ya salió.");
            vehiculoEncontrado = null;
        } else {
            vehiculoEncontrado = resultados.get(0);

            long ingreso = vehiculoEncontrado.getFechaIngreso();
            long ahora = System.currentTimeMillis();
            long diferenciaMs = ahora - ingreso;

            long minutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaMs);
            long horas = minutos / 60;
            long minutosRestantes = minutos % 60;

            String tiempoTexto = (horas > 0)
                    ? horas + "h " + minutosRestantes + " min"
                    : minutos + " min";

            txtResumen.setText("Placa: " + vehiculoEncontrado.getPlaca() +
                    "\nTipo: " + vehiculoEncontrado.getTipoVehiculo() +
                    "\nCelda: " + vehiculoEncontrado.getCelda() +
                    "\nTiempo transcurrido: " + tiempoTexto);
        }
    }
}


