package com.example.newuserinterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newuserinterface.Cliente;
import com.example.newuserinterface.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistroClienteActivity extends AppCompatActivity {

    EditText etNombres, etApellidos, etDocumento, etCorreo, etTelefono, etPlaca, etTipoVehiculo;
    RadioButton rbFrecuente, rbNoFrecuente;
    Button btnRegistrar, btnBuscar, btnActualizar, btnEliminar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);

        // Enlazar vistas
        etNombres = findViewById(R.id.edtNombre);
        etApellidos = findViewById(R.id.edtApellido);
        etDocumento = findViewById(R.id.edtDocumento);
        etCorreo = findViewById(R.id.edtCorreo);
        etTelefono = findViewById(R.id.edtTelefono);
        etPlaca = findViewById(R.id.edtClientePlacas);
        etTipoVehiculo = findViewById(R.id.edtTipoVehiculo);
        rbFrecuente = findViewById(R.id.radioFrecuente);
        rbNoFrecuente = findViewById(R.id.radioNoFrecuente);
        btnRegistrar = findViewById(R.id.btnRegistrarCliente);
        btnBuscar = findViewById(R.id.btnBuscarCliente);
        btnActualizar = findViewById(R.id.btnActualizarCliente);
        btnEliminar = findViewById(R.id.btnEliminarCliente);

        // Acciones
        btnRegistrar.setOnClickListener(v -> registrarCliente());
        btnBuscar.setOnClickListener(v -> buscarCliente());
        btnActualizar.setOnClickListener(v -> actualizarCliente());
        btnEliminar.setOnClickListener(v -> eliminarCliente());
    }

    private void registrarCliente() {
        if (!validarCampos()) return;

        String fechaActual = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault()).format(new Date());

        Cliente cliente = new Cliente(
                etNombres.getText().toString(),
                etApellidos.getText().toString(),
                etDocumento.getText().toString(),
                etCorreo.getText().toString(),
                etTelefono.getText().toString(),
                etPlaca.getText().toString().toUpperCase(),
                etTipoVehiculo.getText().toString(),
                fechaActual,
                rbFrecuente.isChecked()
        );

        cliente.save();
        Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show();
        limpiarCampos();
    }

    private void buscarCliente() {
        String placa = etPlaca.getText().toString().toUpperCase();

        if (placa.isEmpty()) {
            Toast.makeText(this, "Ingrese la placa para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente cliente = Cliente.find(Cliente.class, "placa = ?", placa).stream().findFirst().orElse(null);

        if (cliente != null) {
            etNombres.setText(cliente.getNombres());
            etApellidos.setText(cliente.getApellidos());
            etDocumento.setText(cliente.getDocumento());
            etCorreo.setText(cliente.getCorreo());
            etTelefono.setText(cliente.getTelefono());
            etTipoVehiculo.setText(cliente.getTipoVehiculo());

            if (cliente.isEsFrecuente()) {
                rbFrecuente.setChecked(true);
            } else {
                rbNoFrecuente.setChecked(true);
            }

            Toast.makeText(this, "Cliente encontrado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarCliente() {
        String placa = etPlaca.getText().toString().toUpperCase();

        if (placa.isEmpty()) {
            Toast.makeText(this, "Ingrese la placa para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente cliente = Cliente.find(Cliente.class, "placa = ?", placa).stream().findFirst().orElse(null);

        if (cliente != null) {
            if (!validarCampos()) return;

            cliente.setNombres(etNombres.getText().toString());
            cliente.setApellidos(etApellidos.getText().toString());
            cliente.setDocumento(etDocumento.getText().toString());
            cliente.setCorreo(etCorreo.getText().toString());
            cliente.setTelefono(etTelefono.getText().toString());
            cliente.setTipoVehiculo(etTipoVehiculo.getText().toString());
            cliente.setEsFrecuente(rbFrecuente.isChecked());

            cliente.save();
            Toast.makeText(this, "Cliente actualizado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarCliente() {
        String placa = etPlaca.getText().toString().toUpperCase();

        if (placa.isEmpty()) {
            Toast.makeText(this, "Ingrese la placa para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente cliente = Cliente.find(Cliente.class, "placa = ?", placa).stream().findFirst().orElse(null);

        if (cliente != null) {
            cliente.delete();
            Toast.makeText(this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos() {
        if (etNombres.getText().toString().isEmpty() || etApellidos.getText().toString().isEmpty() ||
                etDocumento.getText().toString().isEmpty() || etCorreo.getText().toString().isEmpty() ||
                etTelefono.getText().toString().isEmpty() || etPlaca.getText().toString().isEmpty() ||
                etTipoVehiculo.getText().toString().isEmpty() || (!rbFrecuente.isChecked() && !rbNoFrecuente.isChecked())) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        etNombres.setText("");
        etApellidos.setText("");
        etDocumento.setText("");
        etCorreo.setText("");
        etTelefono.setText("");
        etPlaca.setText("");
        etTipoVehiculo.setText("");
        rbFrecuente.setChecked(false);
        rbNoFrecuente.setChecked(false);
    }
}






