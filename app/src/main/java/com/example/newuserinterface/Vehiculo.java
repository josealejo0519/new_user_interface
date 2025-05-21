package com.example.newuserinterface;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Vehiculo extends SugarRecord<Vehiculo> {

    private String placa;
    private String tipoVehiculo;
    private String celda;
    private long fechaIngreso;
    private long fechaSalida;

    // Nuevos campos con fecha legible
    private String fechaIngresoTexto;
    private String fechaSalidaTexto;

    // Constructor vacío requerido por SugarORM
    public Vehiculo() {}

    // Constructor sin salida (cuando apenas ingresa)
    public Vehiculo(String placa, String tipoVehiculo, String celda, long fechaIngreso) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.celda = celda;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault());
        this.fechaIngresoTexto = sdf.format(new Date(fechaIngreso));
        this.fechaSalidaTexto = "";
    }

    // Constructor completo
    public Vehiculo(String placa, String tipoVehiculo, String celda, long fechaIngreso, long fechaSalida) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.celda = celda;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault());
        this.fechaIngresoTexto = sdf.format(new Date(fechaIngreso));
        this.fechaSalidaTexto = fechaSalida > 0 ? sdf.format(new Date(fechaSalida)) : "";
    }

    // Getters y Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getCelda() {
        return celda;
    }

    public void setCelda(String celda) {
        this.celda = celda;
    }

    public long getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(long fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault());
        this.fechaIngresoTexto = sdf.format(new Date(fechaIngreso));
    }

    public long getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(long fechaSalida) {
        this.fechaSalida = fechaSalida;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.getDefault());
        this.fechaSalidaTexto = sdf.format(new Date(fechaSalida));
    }

    public String getFechaIngresoTexto() {
        return fechaIngresoTexto;
    }

    public String getFechaSalidaTexto() {
        return fechaSalidaTexto;
    }

    public boolean estaEnParqueadero() {
        return this.fechaSalida == 0;
    }
    public void setFechaIngresoTexto(String fechaIngresoTexto) {
        this.fechaIngresoTexto = fechaIngresoTexto;
    }

    public void setFechaSalidaTexto(String fechaSalidaTexto) {
        this.fechaSalidaTexto = fechaSalidaTexto;
    }


    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", tipo='" + tipoVehiculo + '\'' +
                ", celda='" + celda + '\'' +
                ", ingreso=" + fechaIngresoTexto +
                ", salida=" + (fechaSalida > 0 ? fechaSalidaTexto : "--") +
                '}';
    }
}


