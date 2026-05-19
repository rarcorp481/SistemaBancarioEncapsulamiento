package com.taller.models;

public class Cliente {
    private String nombre;
    private String apellido;
    private CuentaBancaria cuenta;

    public Cliente(String nombre, String apellido, CuentaBancaria cuentaBancaria){
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuenta = cuentaBancaria;
    }

    public Cliente() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }
}
