package com.taller.validators;

import java.util.function.Function;

public class ValidatorUnits {

        public static <T extends RuntimeException> void validarCampoObligatorio(
            String campo,
            String mensaje,
            Function<String, T> exceptionFactory
        ) {
            if (campo == null || campo.isBlank()) {
                throw exceptionFactory.apply(mensaje);
            }
        }

        public static <T extends RuntimeException> void validarNoNulo(
                Object object,
                String mensaje,
                Function<String, T> exceptionFactory
        ){
            if (object == null) {
                throw exceptionFactory.apply(mensaje);
            }
        }

        public static <N extends Number, E extends RuntimeException> void validarNoNegativo(
                N valor,
                String mensaje,
                Function<String, E> exceptionFactory
        ) {
            if (valor == null || valor.doubleValue() < 0) {
                throw exceptionFactory.apply(mensaje);
            }
        }
}
