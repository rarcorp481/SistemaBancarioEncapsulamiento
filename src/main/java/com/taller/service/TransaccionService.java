package com.taller.service;

import com.taller.contracts.ITransaccionService;
import com.taller.contracts.ICuentaService;
import com.taller.contracts.IClienteService;
import com.taller.models.Cliente;
import com.taller.models.Transaccion;
import com.taller.persistance.Global;
import com.taller.validators.TransaccionValidator;

import java.util.List;

public class TransaccionService implements ITransaccionService {

    private final TransaccionValidator transaccionValidator;
    private final ICuentaService cuentaService;
    private final IClienteService clienteService;

    public TransaccionService(TransaccionValidator transaccionValidator, ICuentaService cuentaService, IClienteService clienteService) {
        this.transaccionValidator = transaccionValidator;
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }

    @Override
    public void registrarTransaccion(Transaccion transaccion) {
        transaccionValidator.validarTransaccion(transaccion);
        Global.listaTransacciones.add(transaccion);
    }

    @Override
    public void realizarTransaccion(Transaccion transaccion) {
        transaccionValidator.validarTransaccionNula(transaccion);
        transaccionValidator.validarTipo(transaccion);

        switch (transaccion.getTipo()) {
          case DEPOSITO -> procesarDeposito(transaccion);
          case RETIRO -> procesarRetiro(transaccion);
          case TRANSFERENCIA -> procesarTransferencia(transaccion);
        }

        registrarTransaccion(transaccion);

    }

    @Override
    public List<Transaccion> listarTransacciones() {
        return Global.listaTransacciones;
    }

    private void procesarDeposito(Transaccion transaccion) {
        transaccionValidator.validarDeposito(transaccion);
        Cliente clienteDestino = clienteService.buscarClientePorId(transaccion.getIdUsuarioDestino());
        cuentaService.depositar(clienteDestino.getCuenta().getNumeroCuenta(), transaccion.getMonto());

    }

    private void procesarRetiro(Transaccion transaccion) {
        transaccionValidator.validarRetiro(transaccion);
        Cliente clienteOrigen = clienteService.buscarClientePorId(transaccion.getIdUsuarioOrigen());
        cuentaService.retirar(clienteOrigen.getCuenta().getNumeroCuenta(), transaccion.getMonto());

    }

    private void procesarTransferencia(Transaccion transaccion) {
        transaccionValidator.validarTransferencia(transaccion);
        Cliente clienteOrigen = clienteService.buscarClientePorId(transaccion.getIdUsuarioOrigen());
        Cliente clienteDestino = clienteService.buscarClientePorId(transaccion.getIdUsuarioDestino());
        cuentaService.transferir(
                clienteOrigen.getCuenta().getNumeroCuenta(),
                clienteDestino.getCuenta().getNumeroCuenta(),
                transaccion.getMonto()
        );
    }
}
