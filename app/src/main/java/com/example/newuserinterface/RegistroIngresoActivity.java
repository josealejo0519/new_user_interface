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
import java.util.Locale;

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

        // Permitir edición manual de fecha
        edtFechaIng.setEnabled(true);
        edtFechaIng.setHint("dd/MM/yyyy HH:mm");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placa = edtPlaca.getText().toString().trim();
                String tipo = edtTipoVehiculo.getText().toString().trim();
                String celda = edtCelda.getText().toString().trim();
                String fechaTexto = edtFechaIng.getText().toString().trim();

                // Validación de campos vacíos
                if (placa.isEmpty() || tipo.isEmpty() || celda.isEmpty() || fechaTexto.isEmpty()) {
                    Toast.makeText(RegistroIngresoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar formato de placa y celda (alfanuméricos)
                if (!placa.matches("^[A-Za-z0-9]{5,7}$")) {
                    Toast.makeText(RegistroIngresoActivity.this, "Placa inválida (Ej: ABC123 o LRD34K)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!celda.matches("^[A-Za-z0-9]{2,5}$")) {
                    Toast.makeText(RegistroIngresoActivity.this, "Celda inválida (Ej: 04A)", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar formato de fecha y hora
                long ingreso;
                try {
                    // Reemplaza guiones y espacios por barras
                    String fechaNormalizada = fechaTexto.replace("-", "/").replace(" ", "/");

                    // Usa el formato fijo con barras
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm", Locale.getDefault());
                    Date fecha = sdf.parse(fechaNormalizada);
                    ingreso = fecha.getTime();
                } catch (ParseException e) {
                    Toast.makeText(RegistroIngresoActivity.this, "Formato de fecha inválido (usa dd/MM/yyyy HH:mm)", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar en base de datos
                Vehiculo vehiculo = new Vehiculo(placa, tipo, celda, ingreso);
                vehiculo.save();

                Toast.makeText(RegistroIngresoActivity.this, "Ingreso registrado correctamente", Toast.LENGTH_SHORT).show();

                // Limpiar campos
                edtPlaca.setText("");
                edtTipoVehiculo.setText("");
                edtCelda.setText("");
                edtFechaIng.setText("");
            }
        });
    }
}


