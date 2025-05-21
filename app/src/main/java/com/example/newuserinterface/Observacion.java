package com.example.newuserinterface;

import com.orm.SugarRecord;

public class Observacion extends SugarRecord<Observacion> {

    private String placa;
    private String descripcion;
    private long fecha;

    // Constructor vacío requerido por SugarORM
    public Observacion() {}

    public Observacion(String placa, String descripcion, long fecha) {
        this.placa = placa;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
