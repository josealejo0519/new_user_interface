package com.example.newuserinterface;

import com.orm.SugarRecord;

public class Vehiculo extends SugarRecord {

    private String placa;
    private String tipoVehiculo;
    private String celda;
    private long fechaIngreso;
    private long fechaSalida;

    // Constructor vacío requerido por SugarORM
    public Vehiculo() {
    }

    // Constructor con campos principales (sin salida aún)
    public Vehiculo(String placa, String tipoVehiculo, String celda, long fechaIngreso) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.celda = celda;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = 0; // inicializa como 0 si aún no ha salido
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
    }

    public long getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(long fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
