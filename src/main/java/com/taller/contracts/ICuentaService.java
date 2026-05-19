package com.taller.contracts;

import com.taller.models.CuentaBancaria;

import java.util.List;

public interface ICuentaService {
    void registrarCuenta(CuentaBancaria cuenta);

    CuentaBancaria buscarCuenta(String numeroCuenta);

    List<CuentaBancaria> listarCuentas();

    void depositar(String numeroCuenta, double monto);

    void retirar(String numeroCuenta, double monto);

    void transferir(String numeroCuentaOrigen, String numeroCuentaDestino, double monto);
}
