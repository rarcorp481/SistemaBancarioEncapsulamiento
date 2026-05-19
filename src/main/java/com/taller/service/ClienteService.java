package com.taller.service;

import com.taller.contracts.IClienteService;
import com.taller.exceptions.ClienteInvalidoException;
import com.taller.exceptions.ClienteNoEncontradoException;
import com.taller.models.Cliente;
import com.taller.persistance.Global;
import com.taller.validators.ClienteValidator;
import com.taller.validators.ValidatorUnits;

import java.util.List;

public class ClienteService implements IClienteService {

    private final ClienteValidator clienteValidator;

    public ClienteService(ClienteValidator clienteValidator) {
        this.clienteValidator = clienteValidator;
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        clienteValidator.validarCliente(cliente);
        Global.listaClientes.add(cliente);
    }

    @Override
    public Cliente buscarClientePorId(Integer id) {
        ValidatorUnits.validarNoNulo(
                id,
                "El ID es obligatorio.",
                ClienteInvalidoException::new
        );

        for (Cliente cliente : Global.listaClientes) {
            if (cliente != null && cliente.getId().equals(id)) {
                return cliente;
            }
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado.");
    }

    @Override
    public Cliente buscarCliente(String nombre, String apellido) {
        ValidatorUnits.validarCampoObligatorio(
                nombre,
                "El Nombre del cliente es obligatorio.",
                ClienteInvalidoException::new
        );

        ValidatorUnits.validarCampoObligatorio(
                apellido,
                "El Apellido del cliente es obligatorio",
                ClienteInvalidoException::new
        );

        String nombreBuscado = nombre.toLowerCase().trim();
        String apellidoBuscado = apellido.toLowerCase().trim();

        for (Cliente cliente : Global.listaClientes) {
            if (
                    cliente != null
                    && cliente.getNombre() != null
                    && cliente.getApellido() != null
                    && nombreBuscado.equals(cliente.getNombre().trim().toLowerCase())
                    && apellidoBuscado.equals(cliente.getApellido().trim().toLowerCase())
            ) {
                return cliente;
            }
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado.");
    }

    @Override
    public List<Cliente> listarClientes() {
        return Global.listaClientes;
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        clienteValidator.validarCliente(cliente);

      for (int i = 0; i < Global.listaClientes.size(); i++) {
          Cliente clienteAux = Global.listaClientes.get(i);

          if (clienteAux != null && cliente.getId().equals(clienteAux.getId())) {
              Global.listaClientes.set(i, cliente);
              return;
          }
      }
      throw new ClienteNoEncontradoException("Cliente no encontrado.");
    }

  @Override
  public void eliminarCliente(Cliente cliente) {
      clienteValidator.validarCliente(cliente);

      boolean eliminado = Global.listaClientes.removeIf(
              clienteAux -> clienteAux != null && cliente.getId().equals(clienteAux.getId())
      );

      if (!eliminado) {
          throw new ClienteNoEncontradoException("Cliente no encontrado.");
      }
  }
}
