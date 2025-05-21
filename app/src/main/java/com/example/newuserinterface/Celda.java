package com.example.newuserinterface;

import com.orm.SugarRecord;

public class Celda extends SugarRecord<Celda> {
    private String codigo;  // Ej: A1, M5, etc.
    private boolean disponible;  // true = libre, false = ocupada

    public Celda() {}

    public Celda(String codigo, boolean disponible) {
        this.codigo = codigo;
        this.disponible = disponible;
    }

    public String getCodigo() { return codigo; }
    public boolean isDisponible() { return disponible; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
