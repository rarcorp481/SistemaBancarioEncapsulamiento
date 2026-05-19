package com.taller.validators;

import com.taller.exceptions.TransaccionInvalidaException;
import com.taller.models.Transaccion;

public class TransaccionValidator {

    public void validarTransaccion(Transaccion transaccion){
        validarTransaccionNula(transaccion);
        validarTipo(transaccion);
        validarMonto(transaccion);
        validarDescripcion(transaccion);
        validarMetodo(transaccion);
    }

    public void validarTransaccionNula(Transaccion transaccion){
        ValidatorUnits.validarNoNulo(
                transaccion,
                "La transacción es obligatoria.",
                TransaccionInvalidaException::new
        );
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
