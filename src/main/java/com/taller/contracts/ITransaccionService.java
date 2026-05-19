package com.taller.contracts;

import com.taller.models.Transaccion;

import java.util.List;

public interface ITransaccionService {
    void registrarTransaccion(Transaccion transaccion);

    void realizarTransaccion(Transaccion transaccion);

    List<Transaccion> listarTransacciones();
}
