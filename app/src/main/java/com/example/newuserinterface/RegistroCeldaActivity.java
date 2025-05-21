package com.example.newuserinterface;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RegistroCeldaActivity extends AppCompatActivity {

    private LinearLayout containerCeldas;
    private final String[] codigos = {
            "A1", "A2", "A3", "A4", "A5", // Autos
            "C1", "C2", "C3", "C4", "C5", // Camiones
            "M1", "M2", "M3", "M4", "M5"  // Motos
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_celda);

        containerCeldas = findViewById(R.id.containerCeldas);

        crearCeldasSiNoExisten();
        sincronizarDisponibilidadConVehiculos();
        mostrarCeldas();
    }

    private void crearCeldasSiNoExisten() {
        for (String codigo : codigos) {
            List<Celda> existentes = Celda.find(Celda.class, "codigo = ?", codigo);
            if (existentes.isEmpty()) {
                new Celda(codigo, true).save();
            }
        }
    }

    private void sincronizarDisponibilidadConVehiculos() {
        for (String codigo : codigos) {
            List<Celda> celdas = Celda.find(Celda.class, "codigo = ?", codigo);
            if (!celdas.isEmpty()) {
                Celda celda = celdas.get(0);
                boolean ocupada = !Vehiculo.find(Vehiculo.class, "celda = ? AND fecha_salida = 0", codigo).isEmpty();
                celda.setDisponible(!ocupada);
                celda.save();
            }
        }
    }

    private void mostrarCeldas() {
        containerCeldas.removeAllViews();

        LinearLayout filaAutos = crearColumnaCeldas("A");
        LinearLayout filaCamiones = crearColumnaCeldas("C");
        LinearLayout filaMotos = crearColumnaCeldas("M");

        LinearLayout filaPrincipal = new LinearLayout(this);
        filaPrincipal.setOrientation(LinearLayout.HORIZONTAL);
        filaPrincipal.setGravity(Gravity.CENTER);
        filaPrincipal.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        filaPrincipal.addView(filaAutos);
        filaPrincipal.addView(filaCamiones);
        filaPrincipal.addView(filaMotos);

        containerCeldas.addView(filaPrincipal);
    }

    private LinearLayout crearColumnaCeldas(String tipo) {
        LinearLayout columna = new LinearLayout(this);
        columna.setOrientation(LinearLayout.VERTICAL);
        columna.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        columna.setPadding(8, 0, 8, 0);

        for (String codigo : codigos) {
            if (!codigo.startsWith(tipo)) continue;
            List<Celda> lista = Celda.find(Celda.class, "codigo = ?", codigo);
            if (lista.isEmpty()) continue;

            Celda celda = lista.get(0);
            Button btn = new Button(this);
            btn.setText(codigo + (celda.isDisponible() ? "\nDisponible" : "\nOcupada"));
            btn.setTextColor(Color.WHITE);
            btn.setAllCaps(false);

            GradientDrawable background = new GradientDrawable();
            background.setCornerRadius(20);
            background.setColor(celda.isDisponible() ? Color.parseColor("#69A3AB") : Color.parseColor("#005F5F"));
            btn.setBackground(background);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            btn.setLayoutParams(params);

            btn.setOnClickListener(v -> Toast.makeText(
                    this,
                    "Celda " + codigo + (celda.isDisponible() ? " está disponible" : " está ocupada"),
                    Toast.LENGTH_SHORT
            ).show());

            columna.addView(btn);
        }

        return columna;
    }
}





