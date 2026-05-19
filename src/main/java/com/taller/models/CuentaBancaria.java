package com.taller.models;

public class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;
    private boolean activa;
    private TarjetaDebito tarjeta;

    public CuentaBancaria(String numeroCuenta, double saldo, boolean activa,  TarjetaDebito tarjeta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.activa = activa;
        this.tarjeta = tarjeta;
    }

    public CuentaBancaria() {}

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public TarjetaDebito getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaDebito tarjeta) {
        this.tarjeta = tarjeta;
    }
}
