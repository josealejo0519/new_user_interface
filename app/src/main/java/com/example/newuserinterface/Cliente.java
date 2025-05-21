package com.example.newuserinterface;

import com.orm.SugarRecord;

public class Cliente extends SugarRecord<Cliente> {
    private String nombres;
    private String apellidos;
    private String documento;
    private String correo;
    private String telefono;
    private String placa;
    private String tipoVehiculo;
    private String fechaRegistro;
    private boolean esFrecuente;

    // Constructor vacío requerido por SugarORM
    public Cliente() {
    }

    // Constructor con todos los campos
    public Cliente(String nombres, String apellidos, String documento, String correo,
                   String telefono, String placa, String tipoVehiculo, String fechaRegistro,
                   boolean esFrecuente) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.correo = correo;
        this.telefono = telefono;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaRegistro = fechaRegistro;
        this.esFrecuente = esFrecuente;
    }

    // Getters y Setters

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEsFrecuente() {
        return esFrecuente;
    }

    public void setEsFrecuente(boolean esFrecuente) {
        this.esFrecuente = esFrecuente;
    }
}