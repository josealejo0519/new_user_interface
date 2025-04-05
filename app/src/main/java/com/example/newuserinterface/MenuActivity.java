package com.example.newuserinterface;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    ListView list;
    ArrayAdapter<AppModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        list = findViewById(R.id.list);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("extra");

            if ("apps".equals(value)) {
                List<AppModel> apps = new ArrayList<>();
                apps.add(new AppModel("Enduro", "App Motos", "Explora rutas extremas"));
                apps.add(new AppModel("Turismo", "Viajes en Ruta", "Planea viajes con tu moto"));
                apps.add(new AppModel("Servicio", "Mantenimiento", "Controla revisiones y gastos"));

                adapter = new AppAdapter(this, apps);
            } else if ("categorias".equals(value)) {
                adapter = new CategoryAdapter(this);
            } else {
                adapter = new MenuAdapter(this);
            }

            list.setAdapter(adapter);
        }
    }
}
