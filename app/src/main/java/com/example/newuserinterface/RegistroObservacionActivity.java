package com.example.newuserinterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistroObservacionActivity extends AppCompatActivity {

    private EditText edtPlacaObs, edtDescripcion;
    private Button btnGuardarObs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_observacion);

        edtPlacaObs = findViewById(R.id.edtPlacaObs);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnGuardarObs = findViewById(R.id.btnGuardarObs);

        btnGuardarObs.setOnClickListener(v -> {
            String placa = edtPlacaObs.getText().toString().trim();
            String descripcion = edtDescripcion.getText().toString().trim();

            if (placa.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            long fechaActual = System.currentTimeMillis();
            Observacion obs = new Observacion(placa, descripcion, fechaActual);
            obs.save();

            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(fechaActual));
            Toast.makeText(this, "Observación registrada el " + fechaFormateada, Toast.LENGTH_LONG).show();

            edtPlacaObs.setText("");
            edtDescripcion.setText("");
        });
    }
}

