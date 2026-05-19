package com.taller.service;


import com.taller.contracts.ITarjetaService;
import com.taller.exceptions.TarjetaInvalidaException;
import com.taller.exceptions.TarjetaNoEncontradaException;
import com.taller.models.TarjetaDebito;
import com.taller.persistance.Global;
import com.taller.validators.TarjetaValidator;
import com.taller.validators.ValidatorUnits;

import java.util.List;

public class TarjetaService implements ITarjetaService {

    private final TarjetaValidator tarjetaValidator;

    public TarjetaService(TarjetaValidator tarjetaValidator) {
        this.tarjetaValidator = tarjetaValidator;
    }

    @Override
    public void registrarTarjeta(TarjetaDebito tarjeta) {
        tarjetaValidator.validatTarjeta(tarjeta);
        Global.listaTarjetas.add(tarjeta);
    }

    @Override
    public TarjetaDebito buscarTarjeta(String numeroTarjeta) {
        ValidatorUnits.validarCampoObligatorio(
                numeroTarjeta,
                "El número de tarjeta es obligatorio.",
                TarjetaInvalidaException::new
        );

        for (TarjetaDebito tarjeta : Global.listaTarjetas) {
            if (tarjeta != null && numeroTarjeta.trim().equals(tarjeta.getNumeroTarjeta())) {
                return tarjeta;
            }
        }

        throw new TarjetaNoEncontradaException("Tarjeta no encontrada.");
    }

    @Override
    public List<TarjetaDebito> listarTarjetas() {
        return Global.listaTarjetas;
    }

    @Override
    public void bloquearTarjeta(String numeroTarjeta) {
        TarjetaDebito tarjeta = buscarTarjeta(numeroTarjeta);
        tarjetaValidator.validarTarjeta(tarjeta);
        tarjeta.setBloqueada(true);
    }

    @Override
    public void desbloquearTarjeta(String numeroTarjeta) {
        TarjetaDebito tarjeta = buscarTarjeta(numeroTarjeta);
        tarjetaValidator.validarTarjetaNoNula(tarjeta);
        tarjeta.setBloqueada(false);
    }

    @Override
    public boolean validarPin(String numeroTarjeta, String pin) {
        ValidatorUnits.validarCampoObligatorio(
                pin,
                "El pin es obligatorio.",
                TarjetaInvalidaException::new
        );

        TarjetaDebito tarjeta = buscarTarjeta(numeroTarjeta);
        tarjetaValidator.validarTarjetaNoNula(tarjeta);

        return tarjeta.getPin().equals(pin.trim());
    }
}
