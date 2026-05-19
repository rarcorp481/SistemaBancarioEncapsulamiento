package com.taller.contracts;

import com.taller.models.Cliente;

import java.util.List;

public interface IClienteService {
    void registrarCliente(Cliente cliente);

    Cliente buscarClientePorId(Integer id);

    Cliente buscarCliente(String nombre, String apellido);

    List<Cliente> listarClientes();

    void actualizarCliente(Cliente cliente);

    void eliminarCliente(Cliente cliente);
}
