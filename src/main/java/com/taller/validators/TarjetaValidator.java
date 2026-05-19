package com.taller.validators;

import com.taller.exceptions.TarjetaInvalidaException;
import com.taller.models.TarjetaDebito;

public class TarjetaValidator {

    public void validatTarjeta(TarjetaDebito tarjetaDebito){
        validarTarjeta(tarjetaDebito);
    }

    public void validarTarjeta(TarjetaDebito tarjetaDebito){
        validarTarjetaNoNula(tarjetaDebito);
        validarNumeroTarjeta(tarjetaDebito);
        validarPin(tarjetaDebito);
    }

    public void validarTarjetaBloqueada(TarjetaDebito tarjetaDebito){
        validarTarjetaNoNula(tarjetaDebito);
        if (!tarjetaDebito.isBloqueada()){
            throw new TarjetaInvalidaException("La tarjeta debe estar bloqueada.");
        }
    }

    public void validarTarjetaNoNula(TarjetaDebito tarjetaDebito){
        ValidatorUnits.validarNoNulo(
                tarjetaDebito,
                "La Tarjeta es obligatoria.",
                TarjetaInvalidaException::new
        );
    }

    public void validarNumeroTarjeta(TarjetaDebito tarjetaDebito){
        ValidatorUnits.validarCampoObligatorio(
                tarjetaDebito.getNumeroTarjeta(),
                "Número de tarjeta inválido. Intente de nuevo.",
                TarjetaInvalidaException::new
        );
        if(!tarjetaDebito.getNumeroTarjeta().matches("^\\d{4} \\d{4} \\d{4} \\d{4}$")){
            throw new TarjetaInvalidaException("Número de tarjeta inválido. Intente de nuevo.");
        }
    }

    public void validarPin(TarjetaDebito tarjetaDebito){
        ValidatorUnits.validarCampoObligatorio(
                tarjetaDebito.getPin(),
                "Pin inválido, Intente de nuevo.",
                TarjetaInvalidaException::new

        );
        if (!tarjetaDebito.getPin().matches("^\\d{4}$")){
            throw new TarjetaInvalidaException("Pin inválido. Intente de nuevo.");
        }
    }

    public void validarIsBloqueo(TarjetaDebito tarjetaDebito){
        validarTarjetaBloqueada(tarjetaDebito);
    }

    public void validarTarjetaDesbloqueada(TarjetaDebito tarjetaDebito){
        validarTarjetaNoNula(tarjetaDebito);
        if (tarjetaDebito.isBloqueada()){
            throw new TarjetaInvalidaException("La tarjeta ya está bloqueada.");
        }
    }
}
