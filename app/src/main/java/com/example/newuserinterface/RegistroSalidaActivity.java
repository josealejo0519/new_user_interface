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
import java.util.TimeZone;
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

                long ingreso = vehiculoEncontrado.getFechaIngreso();
                long diferenciaMs = salida - ingreso;
                long minutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaMs);
                long horas = minutos / 60;
                long minutosRestantes = minutos % 60;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
                String fechaSalida = sdf.format(new Date(salida));

                Toast.makeText(this, "Salida registrada.\nHora: " + fechaSalida +
                        "\nTiempo total: " + horas + "h " + minutosRestantes + "m.", Toast.LENGTH_LONG).show();
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
            long salida = System.currentTimeMillis();

            long diferenciaMs = salida - ingreso;
            long minutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaMs);
            long horas = minutos / 60;
            long minutosRestantes = minutos % 60;

            String tiempoTexto = (horas > 0)
                    ? horas + "h " + minutosRestantes + " min"
                    : minutos + " min";

            int tarifaHora = 500;
            int valorPagar = (int) Math.max(1, Math.ceil((double) minutos / 60)) * tarifaHora;

            txtResumen.setText("Placa: " + vehiculoEncontrado.getPlaca() +
                    "\nTipo: " + vehiculoEncontrado.getTipoVehiculo() +
                    "\nCelda: " + vehiculoEncontrado.getCelda() +
                    "\nTiempo transcurrido: " + tiempoTexto +
                    "\nValor a pagar: $" + valorPagar);
        }
    }
}


