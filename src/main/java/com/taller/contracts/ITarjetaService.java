package com.taller.contracts;

import com.taller.models.TarjetaDebito;

import java.util.List;

public interface ITarjetaService {
    void registrarTarjeta(TarjetaDebito tarjeta);

    TarjetaDebito buscarTarjeta(String numeroTarjeta);

    List<TarjetaDebito> listarTarjetas();

    void bloquearTarjeta(String numeroTarjeta);

    void desbloquearTarjeta(String numeroTarjeta);

    boolean validarPin(String numeroTarjeta, String pin);
}
