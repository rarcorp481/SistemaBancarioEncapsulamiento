package com.taller.validators;

import com.taller.exceptions.ClienteInvalidoException;
import com.taller.models.Cliente;

public class ClienteValidator {

    public void validarCliente(Cliente cliente){
        validarClienteNulo(cliente);
        validarIdCliente(cliente);
        validarNombreCompleto(cliente);
        validarCuenta(cliente);

    }

    public void validarClienteNulo(Cliente cliente){
        ValidatorUnits.validarNoNulo(
                cliente,
                "El cliente es obligatorio.",
                ClienteInvalidoException::new
        );
    }

    public void validarIdCliente(Cliente cliente){
        ValidatorUnits.validarNoNulo(
                cliente.getId(),
                "El ID es obligatorio",
                ClienteInvalidoException::new
        );
    }

    public void validarNombre(Cliente cliente){
        ValidatorUnits.validarCampoObligatorio(
                cliente.getNombre(),
                "La nombre es obligatorio.",
                ClienteInvalidoException::new
        );
    }

    public void validarApellido(Cliente cliente){
        ValidatorUnits.validarCampoObligatorio(
                cliente.getApellido(),
                "El apellido es obligatorio.",
                ClienteInvalidoException::new
        );
    }

    public void validarNombreCompleto(Cliente cliente){
        validarNombre(cliente);
        validarApellido(cliente);
    }

    public void validarCuenta(Cliente cliente){
        ValidatorUnits.validarNoNulo(
                cliente.getCuenta(),
                "La cuenta es obligatoria.",
                ClienteInvalidoException::new
        );
    }
}
