package com.taller.service;

import com.taller.contracts.ICuentaService;
import com.taller.exceptions.CuentaNoEncontradaException;
import com.taller.models.CuentaBancaria;
import com.taller.persistance.Global;
import com.taller.validators.CuentaValidator;
import com.taller.validators.ValidatorUnits;

import com.taller.exceptions.CuentaInvalidaException;
import com.taller.exceptions.SaldoInvalidoException;

import java.util.List;

public class CuentaService implements ICuentaService {

    private final CuentaValidator cuentaValidator;

    public CuentaService(CuentaValidator cuentaValidator) {
        this.cuentaValidator = cuentaValidator;
    }

    @Override
    public void registrarCuenta(CuentaBancaria cuenta) {
        cuenta.setNumeroCuenta(cuentaValidator.normalizarNumeroCuenta(cuenta.getNumeroCuenta()));
        cuentaValidator.validarCuentaBancaria(cuenta);
        Global.listaCuentaBancarias.add(cuenta);
    }

    @Override
    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        String numeroCuentaNormalizado = cuentaValidator.normalizarNumeroCuenta(numeroCuenta);
        ValidatorUnits.validarCampoObligatorio(
                numeroCuentaNormalizado,
                "El número de cuenta es obligatorio.",
                CuentaInvalidaException::new
        );

        for (CuentaBancaria cuenta : Global.listaCuentaBancarias) {
            if (cuenta != null && numeroCuentaNormalizado.equals(cuenta.getNumeroCuenta())) {
                return cuenta;
            }
        }

        throw new CuentaNoEncontradaException("Cuenta no encontrada.");
    }

    @Override
    public List<CuentaBancaria> listarCuentas() {
        return Global.listaCuentaBancarias;
    }

    @Override
    public void depositar(String numeroCuenta, double monto) {
        validarMonto(monto);
        CuentaBancaria cuenta = buscarCuenta(numeroCuenta);
        cuentaValidator.validarCuentaBancaria(cuenta);
        cuenta.setSaldo(cuenta.getSaldo() + monto);
    }

    @Override
    public void retirar(String numeroCuenta, double monto) {
        validarMonto(monto);
        CuentaBancaria cuenta = buscarCuenta(numeroCuenta);
        cuentaValidator.validarCuentaBancaria(cuenta);
        validarSaldoSuficiente(cuenta, monto);
        cuenta.setSaldo(cuenta.getSaldo() - monto);
    }

    @Override
    public void transferir(String numeroCuentaOrigen, String numeroCuentaDestino, double monto) {
        validarMonto(monto);
        CuentaBancaria cuentaOrigen = buscarCuenta(numeroCuentaOrigen);
        CuentaBancaria cuentaDestino = buscarCuenta(numeroCuentaDestino);

        cuentaValidator.validarCuentaBancaria(cuentaOrigen);
        cuentaValidator.validarCuentaBancaria(cuentaDestino);
        validarSaldoSuficiente(cuentaOrigen, monto);

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
    }

    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new CuentaInvalidaException("El monto debe ser mayor que cero.");
        }
    }

    private void validarSaldoSuficiente(CuentaBancaria cuenta, double monto) {
        if (cuenta.getSaldo() < monto) {
            throw new SaldoInvalidoException("Saldo insuficiente.");
        }
    }
}
