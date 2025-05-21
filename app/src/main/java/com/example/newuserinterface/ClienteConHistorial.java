package com.example.newuserinterface;

import java.util.List;

public class ClienteConHistorial {
    private Cliente cliente;
    private List<Vehiculo> historialVehiculos;

    public ClienteConHistorial(Cliente cliente, List<Vehiculo> historialVehiculos) {
        this.cliente = cliente;
        this.historialVehiculos = historialVehiculos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Vehiculo> getHistorialVehiculos() {
        return historialVehiculos;
    }

    public void setHistorialVehiculos(List<Vehiculo> historialVehiculos) {
        this.historialVehiculos = historialVehiculos;
    }
}
