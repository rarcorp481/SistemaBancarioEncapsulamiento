package com.taller.models;

public class Cliente {
    private static Integer secuenciaId = 1;

    private Integer id;
    private String nombre;
    private String apellido;
    private CuentaBancaria cuenta;

    public Cliente(String nombre, String apellido, CuentaBancaria cuentaBancaria){
        this.id = generarIdAutomatico();
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuenta = cuentaBancaria;
    }

    public Cliente() {
        this.id = generarIdAutomatico();
    }

    private Integer generarIdAutomatico() {
        return secuenciaId++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        if (id != null && id >= secuenciaId) {
            secuenciaId = id + 1;
        }
    }

    public String getIdFormateado() {
        return id == null ? null : String.format("%04d", id);
    }

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
