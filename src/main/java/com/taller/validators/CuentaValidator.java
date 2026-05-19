package com.taller.validators;

import com.taller.exceptions.CuentaInvalidaException;
import com.taller.models.CuentaBancaria;


public class CuentaValidator {

    public void validarCuentaBancaria(CuentaBancaria cuenta){
        validarCuentaNula(cuenta);
        validarNumeroCuenta(cuenta);
        validarSaldo(cuenta);
        validarActivo(cuenta);
        validarTarjeta(cuenta);
    }

    public void validarCuentaNula(CuentaBancaria cuenta){
        ValidatorUnits.validarNoNulo(
                cuenta,
                "La cuenta es obligatoria.",
                CuentaInvalidaException::new
        );
    }

    public void validarNumeroCuenta(CuentaBancaria cuenta){
        ValidatorUnits.validarCampoObligatorio(
                cuenta.getNumeroCuenta(),
                "El número de cuenta es obligatorio",
                CuentaInvalidaException::new
        );
    }

    public void validarSaldo(CuentaBancaria cuenta){
        ValidatorUnits.validarNoNegativo(
                cuenta.getSaldo(),
                "El saldo no puede ser negativo",
                CuentaInvalidaException::new
        );
    }

    public void validarActivo(CuentaBancaria cuenta){
        if (!cuenta.isActiva()){
            throw new CuentaInvalidaException("Operación inválida. Cuenta no activa.");
        }
    }

    public void validarTarjeta(CuentaBancaria cuenta){
        ValidatorUnits.validarNoNulo(
                cuenta.getTarjeta(),
                "La cuenta no tiene tarjeta de débito asociada.",
                CuentaInvalidaException::new
        );
    }
}
