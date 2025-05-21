package com.example.newuserinterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newuserinterface.Cliente;

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

        edtTipoVehiculo.setEnabled(false); // Deshabilitamos edición manual
        edtFechaIng.setEnabled(false);
        edtFechaIng.setHint("dd/MM/yyyy HH:mm");

        SimpleDateFormat sdfColombia = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        sdfColombia.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        String fechaActual = sdfColombia.format(new Date());
        edtFechaIng.setText(fechaActual);

        // Buscar tipo de vehículo automáticamente al dejar de escribir placa
        edtPlaca.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String placaIngresada = edtPlaca.getText().toString().trim().toUpperCase();
                if (!placaIngresada.isEmpty()) {
                    Cliente cliente = Cliente.find(Cliente.class, "placa = ?", placaIngresada).stream().findFirst().orElse(null);
                    if (cliente != null) {
                        edtTipoVehiculo.setText(cliente.getTipoVehiculo());
                    } else {
                        edtTipoVehiculo.setText("");
                        Toast.makeText(this, "Cliente no registrado con esta placa", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegistrar.setOnClickListener(v -> {
            String placa = edtPlaca.getText().toString().trim().toUpperCase();
            String tipo = edtTipoVehiculo.getText().toString().trim().toLowerCase();
            String celda = edtCelda.getText().toString().trim().toUpperCase();
            String fechaTexto = edtFechaIng.getText().toString().trim();

            if (placa.isEmpty() || tipo.isEmpty() || celda.isEmpty() || fechaTexto.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!placa.matches("^[A-Za-z0-9]{5,7}$")) {
                Toast.makeText(this, "Placa inválida (Ej: ABC123 o LRD34K)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!celda.matches("^[A-Z][1-5]$")) {
                Toast.makeText(this, "Formato de celda inválido. Usa A1-A5, C1-C5 o M1-M5", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean celdaValida =
                    (tipo.contains("automovil") && celda.startsWith("A")) ||
                            (tipo.contains("camion") && celda.startsWith("C")) ||
                            (tipo.contains("moto") && celda.startsWith("M"));

            if (!celdaValida) {
                Toast.makeText(this, "Celda no corresponde al tipo de vehículo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar disponibilidad de la celda
            List<Vehiculo> ocupados = Vehiculo.find(Vehiculo.class, "celda = ? AND fecha_salida = 0", celda);
            if (!ocupados.isEmpty()) {
                Toast.makeText(this, "La celda ya está ocupada", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Formato de fecha inválido (usa dd/MM/yyyy HH:mm)", Toast.LENGTH_SHORT).show();
                return;
            }

            Vehiculo vehiculo = new Vehiculo(placa, tipo, celda, ingreso);
            vehiculo.save();

            Toast.makeText(this, "Ingreso registrado correctamente", Toast.LENGTH_SHORT).show();

            edtPlaca.setText("");
            edtTipoVehiculo.setText("");
            edtCelda.setText("");
            edtFechaIng.setText(sdfColombia.format(new Date()));
        });
    }
}