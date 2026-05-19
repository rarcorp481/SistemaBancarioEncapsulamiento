package com.taller.validators;

import com.taller.exceptions.TransaccionInvalidaException;
import com.taller.models.Transaccion;
import com.taller.models.enums.TipoTransaccion;

public class TransaccionValidator {

    public void validarTransaccion(Transaccion transaccion){
        validarTransaccionBase(transaccion);
        validarIdsSegunTipo(transaccion);
    }

    public void validarTransaccionBase(Transaccion transaccion){
        validarTransaccionNula(transaccion);
        validarTipo(transaccion);
        validarMonto(transaccion);
        validarDescripcion(transaccion);
        validarMetodo(transaccion);
    }

    public void validarDeposito(Transaccion transaccion){
        validarTransaccionBase(transaccion);
        validarIdUsuarioDestino(transaccion);
        validarIdUsuarioOrigenNulo(transaccion);
    }

    public void validarRetiro(Transaccion transaccion){
        validarTransaccionBase(transaccion);
        validarIdUsuarioOrigen(transaccion);
        validarIdUsuarioDestinoNulo(transaccion);
    }

    public void validarTransferencia(Transaccion transaccion){
        validarTransaccionBase(transaccion);
        validarIdUsuarioOrigen(transaccion);
        validarIdUsuarioDestino(transaccion);
        validarIdsDiferentes(transaccion);
    }

    public void validarTransaccionNula(Transaccion transaccion){
        ValidatorUnits.validarNoNulo(
                transaccion,
                "La transacción es obligatoria.",
                TransaccionInvalidaException::new
        );
    }

    public void validarIdUsuarioOrigen(Transaccion transaccion){
        ValidatorUnits.validarNoNulo(
                transaccion.getIdUsuarioOrigen(),
                "El id del usuario origen es obligatorio.",
                TransaccionInvalidaException::new
        );
    }

    public void validarIdUsuarioDestino(Transaccion transaccion){
        ValidatorUnits.validarNoNulo(
                transaccion.getIdUsuarioDestino(),
                "El id del usuario destino es obligatorio.",
                TransaccionInvalidaException::new
        );
    }

    public void validarIdUsuarioOrigenNulo(Transaccion transaccion){
        if (transaccion.getIdUsuarioOrigen() != null) {
            throw new TransaccionInvalidaException("El id del usuario origen debe ser nulo para un depósito.");
        }
    }

    public void validarIdUsuarioDestinoNulo(Transaccion transaccion){
        if (transaccion.getIdUsuarioDestino() != null) {
            throw new TransaccionInvalidaException("El id del usuario destino debe ser nulo para un retiro.");
        }
    }

    public void validarIdsDiferentes(Transaccion transaccion){
        if (transaccion.getIdUsuarioOrigen() != null
                && transaccion.getIdUsuarioDestino() != null
                && transaccion.getIdUsuarioOrigen().equals(transaccion.getIdUsuarioDestino())) {
            throw new TransaccionInvalidaException("El usuario origen y destino deben ser diferentes.");
        }
    }

    public void validarIdsSegunTipo(Transaccion transaccion){
        if (transaccion.getTipo() == TipoTransaccion.DEPOSITO) {
            validarDeposito(transaccion);
        }

        if (transaccion.getTipo() == TipoTransaccion.RETIRO) {
            validarRetiro(transaccion);
        }

        if (transaccion.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            validarTransferencia(transaccion);
        }
    }

    public void validarTipo(Transaccion transaccion){
        ValidatorUnits.validarNoNulo(
                transaccion.getTipo(),
                "El tipo de transacción es obligatorio.",
                TransaccionInvalidaException::new
        );
    }

    public void validarMonto(Transaccion transaccion){
        ValidatorUnits.validarNoNegativo(
                transaccion.getMonto(),
                "El monto no puede ser negativo.",
                TransaccionInvalidaException::new
        );
    }

    public void validarDescripcion(Transaccion transaccion){
        ValidatorUnits.validarCampoObligatorio(
                transaccion.getDescripcion(),
                "La descripción es obligatoria.",
                TransaccionInvalidaException::new
        );
        if (transaccion.getDescripcion().trim().length() > 200) {
            throw new TransaccionInvalidaException("La descripción es demasiado larga.");
        }
    }

    public void validarMetodo(Transaccion transaccion){
        ValidatorUnits.validarNoNulo(
                transaccion.getMetodo(),
                "El método de pago es obligatorio.",
                TransaccionInvalidaException::new
        );
    }

}
