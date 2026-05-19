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
        if (!cuenta.getNumeroCuenta().matches("^\\d{1,12}$")) {
            throw new CuentaInvalidaException("El número de cuenta debe tener solo dígitos y máximo 12 caracteres.");
        }
    }

    public String normalizarNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null) {
            return null;
        }
        return numeroCuenta.trim().replace(" ", "");
    }

    public void validarSaldo(CuentaBancaria cuenta){
        ValidatorUnits.validarNoNegativo(
                cuenta.getSaldo(),
                "El saldo no puede ser negativo",
                CuentaInvalidaException::new
        );
    }

    public void validarSaldoInicial(double saldo){
        if (saldo <= 0) {
            throw new CuentaInvalidaException("El saldo inicial debe ser mayor que cero.");
        }
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
