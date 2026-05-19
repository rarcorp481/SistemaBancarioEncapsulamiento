package com.taller.models;

public class TarjetaDebito{
    private String numeroTarjeta;
    private String pin;
    private boolean bloqueada;

    public TarjetaDebito(String numeroTarjeta, String pin, boolean bloqueada) {
        this.numeroTarjeta = numeroTarjeta;
        this.pin = pin;
        this.bloqueada = bloqueada;
    }

    public TarjetaDebito() {}

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }
}
