package com.example.newuserinterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RegistroIngresoActivity extends AppCompatActivity {

    private EditText edtPlaca, edtTipoVehiculo, edtFechaIng, edtCelda;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ingreso);

        edtPlaca = findViewById(R.id.edtPlaca);
        edtTipoVehiculo = findViewById(R.id.edtTipoVehiculo);
        edtFechaIng = findViewById(R.id.edtFechaIng);
        edtCelda = findViewById(R.id.edtCelda);
        btnRegistrar = findViewById(R.id.btnRegistrarEntrada);

        edtFechaIng.setEnabled(true);
        edtFechaIng.setHint("dd/MM/yyyy HH:mm");

        SimpleDateFormat sdfColombia = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        sdfColombia.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        String fechaActual = sdfColombia.format(new Date());
        edtFechaIng.setText(fechaActual);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placa = edtPlaca.getText().toString().trim();
                String tipo = edtTipoVehiculo.getText().toString().trim().toLowerCase();
                String celda = edtCelda.getText().toString().trim().toUpperCase();
                String fechaTexto = edtFechaIng.getText().toString().trim();

                if (placa.isEmpty() || tipo.isEmpty() || celda.isEmpty() || fechaTexto.isEmpty()) {
                    Toast.makeText(RegistroIngresoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!placa.matches("^[A-Za-z0-9]{5,7}$")) {
                    Toast.makeText(RegistroIngresoActivity.this, "Placa inválida (Ej: ABC123 o LRD34K)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!celda.matches("^[A-Z][1-5]$")) {
                    Toast.makeText(RegistroIngresoActivity.this, "Formato de celda inválido. Usa A1-A5, C1-C5 o M1-M5", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean celdaValida =
                        (tipo.contains("automovil") && celda.startsWith("A")) ||
                                (tipo.contains("camion") && celda.startsWith("C")) ||
                                (tipo.contains("moto") && celda.startsWith("M"));

                if (!celdaValida) {
                    Toast.makeText(RegistroIngresoActivity.this, "Celda no corresponde al tipo de vehículo", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar disponibilidad de la celda
                List<Vehiculo> ocupados = Vehiculo.find(Vehiculo.class, "celda = ? AND fecha_salida = 0", celda);
                if (!ocupados.isEmpty()) {
                    Toast.makeText(RegistroIngresoActivity.this, "La celda ya está ocupada", Toast.LENGTH_SHORT).show();
                    return;
                }

                long ingreso;
                try {
                    String fechaNormalizada = fechaTexto.replace("-", "/").replace(" ", "/");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm", Locale.getDefault());
                    sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
                    Date fecha = sdf.parse(fechaNormalizada);
                    ingreso = fecha.getTime();
                } catch (ParseException e) {
                    Toast.makeText(RegistroIngresoActivity.this, "Formato de fecha inválido (usa dd/MM/yyyy HH:mm)", Toast.LENGTH_SHORT).show();
                    return;
                }

                Vehiculo vehiculo = new Vehiculo(placa, tipo, celda, ingreso);
                vehiculo.save();

                Toast.makeText(RegistroIngresoActivity.this, "Ingreso registrado correctamente", Toast.LENGTH_SHORT).show();

                edtPlaca.setText("");
                edtTipoVehiculo.setText("");
                edtCelda.setText("");
                edtFechaIng.setText(sdfColombia.format(new Date()));
            }
        });
    }
}




