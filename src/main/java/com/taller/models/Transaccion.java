package com.taller.models;

import com.taller.models.enums.MetodoPagoTransaccion;
import com.taller.models.enums.TipoTransaccion;

public class Transaccion {
    private Integer idUsuarioOrigen;
    private Integer idUsuarioDestino;
    private TipoTransaccion tipo;
    private double monto;
    private String descripcion;
    private MetodoPagoTransaccion metodo;

    public Transaccion(TipoTransaccion tipo, double monto, String descripcion, MetodoPagoTransaccion metodo) {
        this(null, null, tipo, monto, descripcion, metodo);
    }

    public Transaccion(Integer idUsuarioOrigen, Integer idUsuarioDestino, TipoTransaccion tipo, double monto, String descripcion, MetodoPagoTransaccion metodo) {
        this.idUsuarioOrigen = idUsuarioOrigen;
        this.idUsuarioDestino = idUsuarioDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.metodo = metodo;
    }

    public Transaccion() {}
    public Integer getIdUsuarioOrigen() {
        return idUsuarioOrigen;
    }

    public void setIdUsuarioOrigen(Integer idUsuarioOrigen) {
        this.idUsuarioOrigen = idUsuarioOrigen;
    }

    public Integer getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(Integer idUsuarioDestino) {
        this.idUsuarioDestino = idUsuarioDestino;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MetodoPagoTransaccion getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPagoTransaccion metodo) {
        this.metodo = metodo;
    }
}
