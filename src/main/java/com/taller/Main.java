package com.taller;

import com.taller.contracts.IClienteService;
import com.taller.contracts.ICuentaService;
import com.taller.contracts.ITarjetaService;
import com.taller.contracts.ITransaccionService;
import com.taller.models.Cliente;
import com.taller.models.CuentaBancaria;
import com.taller.models.TarjetaDebito;
import com.taller.models.Transaccion;
import com.taller.models.enums.MetodoPagoTransaccion;
import com.taller.models.enums.TipoTransaccion;
import com.taller.persistance.Global;
import com.taller.service.ClienteService;
import com.taller.service.CuentaService;
import com.taller.service.TarjetaService;
import com.taller.service.TransaccionService;
import com.taller.validators.ClienteValidator;
import com.taller.validators.CuentaValidator;
import com.taller.validators.TarjetaValidator;
import com.taller.validators.TransaccionValidator;

import java.util.Scanner;

public class Main {
    private static int secuenciaTarjetas = 1;

    private static class AccionCanceladaException extends RuntimeException {
    }

    public static void main(String[] args) {
        ClienteValidator clienteValidator = new ClienteValidator();
        CuentaValidator cuentaValidator = new CuentaValidator();
        TarjetaValidator tarjetaValidator = new TarjetaValidator();
        TransaccionValidator transaccionValidator = new TransaccionValidator();

        IClienteService clienteService = new ClienteService(clienteValidator);
        ICuentaService cuentaService = new CuentaService(cuentaValidator);
        ITarjetaService tarjetaService = new TarjetaService(tarjetaValidator);
        ITransaccionService transaccionService = new TransaccionService(transaccionValidator, cuentaService, clienteService);

        seedClientesIniciales(clienteService, cuentaService, tarjetaService);

        Scanner scanner = new Scanner(System.in);
        Cliente clienteSesion = null;
        boolean salir = false;

        mostrarEncabezado();
        while (!salir) {
            mostrarMenu();
            int opcion;
            try {
                opcion = leerEntero(scanner, "Seleccione una opcion: ");
            } catch (AccionCanceladaException e) {
                System.out.println();
                continue;
            }
            System.out.println();

            try {
                switch (opcion) {
                    case 1 -> crearCliente(scanner, cuentaValidator, clienteService, cuentaService, tarjetaService);
                    case 2 -> clienteSesion = iniciarSesion(scanner, clienteService);
                    case 3 -> realizarTransaccion(scanner, transaccionService, clienteService, clienteSesion);
                    case 4 -> listarClientes();
                    case 5 -> listarCuentas();
                    case 6 -> listarTarjetas();
                    case 0 -> {
                        System.out.println("Saliendo...");
                        salir = true;
                    }
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (RuntimeException e) {
                mostrarError(e.getMessage());
            } catch (Exception e) {
                mostrarError("Error inesperado: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void mostrarEncabezado() {
        System.out.println("========================================");
        System.out.println("      GESTION BANCARIA ENCAPSULADA      ");
        System.out.println("========================================");
        System.out.println("Clientes, cuentas, tarjetas y movimientos");
        System.out.println();
    }

    private static void mostrarMenu() {
        System.out.println("=== MENU ===");
        System.out.println("1. Crear cliente");
        System.out.println("2. Iniciar sesion");
        System.out.println("3. Realizar transaccion");
        System.out.println("4. Listar clientes");
        System.out.println("5. Listar cuentas");
        System.out.println("6. Listar tarjetas");
        System.out.println("0. Salir");
    }

    private static void seedClientesIniciales(IClienteService clienteService, ICuentaService cuentaService, ITarjetaService tarjetaService) {
        TarjetaDebito tarjeta1 = crearTarjetaAutogenerada("1234");
        TarjetaDebito tarjeta2 = crearTarjetaAutogenerada("5678");

        CuentaBancaria cuenta1 = new CuentaBancaria("1001", 1000.0, true, tarjeta1);
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", 500.0, true, tarjeta2);

        clienteService.registrarCliente(new Cliente("Juan", "Perez", cuenta1));
        clienteService.registrarCliente(new Cliente("Maria", "Gomez", cuenta2));

        cuentaService.registrarCuenta(cuenta1);
        cuentaService.registrarCuenta(cuenta2);
        tarjetaService.registrarTarjeta(tarjeta1);
        tarjetaService.registrarTarjeta(tarjeta2);
    }

    private static void crearCliente(Scanner scanner, CuentaValidator cuentaValidator, IClienteService clienteService, ICuentaService cuentaService, ITarjetaService tarjetaService) {
        try {
            System.out.println();
            mostrarInstruccionCancelacion();
            String nombre = leerTextoObligatorio(scanner, "Nombre: ");
            String apellido = leerTextoObligatorio(scanner, "Apellido: ");
            String numeroCuenta = leerNumeroCuenta(scanner);
            double saldo = leerSaldoInicial(scanner, cuentaValidator);
            String pin = leerPin(scanner, "Pin de tarjeta (4 digitos): ");

            TarjetaDebito tarjeta = crearTarjetaAutogenerada(pin);
            CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, saldo, true, tarjeta);
            Cliente cliente = new Cliente(nombre, apellido, cuenta);

            tarjetaService.registrarTarjeta(tarjeta);
            cuentaService.registrarCuenta(cuenta);
            clienteService.registrarCliente(cliente);

            System.out.println();
            System.out.println("OK: Cliente creado con ID: " + cliente.getIdFormateado());
            System.out.println("OK: Numero de tarjeta autogenerado: " + tarjeta.getNumeroTarjeta());
            System.out.println();
        } catch (AccionCanceladaException e) {
            System.out.println();
        }
    }

    private static Cliente iniciarSesion(Scanner scanner, IClienteService clienteService) {
        try {
            System.out.println();
            mostrarInstruccionCancelacion();
            String nombre = leerTextoObligatorio(scanner, "Nombre: ");
            String apellido = leerTextoObligatorio(scanner, "Apellido: ");
            Cliente cliente = clienteService.buscarCliente(nombre, apellido);
            if (cliente.getCuenta() == null || cliente.getCuenta().getTarjeta() == null) {
                throw new IllegalStateException("El cliente no tiene tarjeta o cuenta asociada.");
            }

            while (true) {
                String pin = leerPin(scanner, "Pin: ");
                if (cliente.getCuenta().getTarjeta().getPin().equals(pin)) {
                    System.out.println();
                    System.out.println("OK: Sesion iniciada para " + cliente.getNombre() + " " + cliente.getApellido());
                    System.out.println();
                    return cliente;
                }
                mostrarError("Pin incorrecto.");
            }
        } catch (AccionCanceladaException e) {
            System.out.println();
            return null;
        }
    }

    private static void realizarTransaccion(Scanner scanner, ITransaccionService transaccionService, IClienteService clienteService, Cliente clienteSesion) {
        if (clienteSesion == null) {
            mostrarError("Debes iniciar sesion para realizar una transaccion.");
            return;
        }

        try {
            System.out.println();
            mostrarInstruccionCancelacion();
            System.out.println("Tipo de transaccion:");
            System.out.println("1. Deposito");
            System.out.println("2. Retiro");
            System.out.println("3. Transferencia");
            int tipoSeleccionado = leerEntero(scanner, "Seleccione tipo: ");
            System.out.println();

            TipoTransaccion tipoTransaccion = switch (tipoSeleccionado) {
                case 1 -> TipoTransaccion.DEPOSITO;
                case 2 -> TipoTransaccion.RETIRO;
                case 3 -> TipoTransaccion.TRANSFERENCIA;
                default -> null;
            };

            if (tipoTransaccion == null) {
                System.out.println("Tipo invalido.");
                return;
            }

            Cliente clienteOrigen = null;
            Cliente clienteDestino = null;
            MetodoPagoTransaccion metodo = MetodoPagoTransaccion.EFECTIVO;

            double monto = leerDouble(scanner, "Monto: ");
            String descripcion = leerTextoObligatorio(scanner, "Descripcion: ");

            switch (tipoTransaccion) {
                case DEPOSITO -> {
                    String nombreDestino = leerTextoObligatorio(scanner, "Nombre del cliente destino: ");
                    String apellidoDestino = leerTextoObligatorio(scanner, "Apellido del cliente destino: ");
                    clienteDestino = clienteService.buscarCliente(nombreDestino, apellidoDestino);
                    metodo = leerMetodoPago(scanner);
                }
                case RETIRO -> clienteOrigen = clienteSesion;
                case TRANSFERENCIA -> {
                    clienteOrigen = clienteSesion;
                    String nombreDestino = leerTextoObligatorio(scanner, "Nombre del cliente destino: ");
                    String apellidoDestino = leerTextoObligatorio(scanner, "Apellido del cliente destino: ");
                    clienteDestino = clienteService.buscarCliente(nombreDestino, apellidoDestino);
                }
            }

            Transaccion transaccion = new Transaccion();
            transaccion.setTipo(tipoTransaccion);
            transaccion.setMonto(monto);
            transaccion.setDescripcion(descripcion);
            transaccion.setMetodo(metodo);
            transaccion.setIdUsuarioOrigen(clienteOrigen != null ? clienteOrigen.getId() : null);
            transaccion.setIdUsuarioDestino(clienteDestino != null ? clienteDestino.getId() : null);

            transaccionService.realizarTransaccion(transaccion);
            System.out.println();
            System.out.println("OK: Transaccion realizada con exito.");
            System.out.println();
        } catch (AccionCanceladaException e) {
            System.out.println();
        }
    }

    private static void listarClientes() {
        for (Cliente cliente : Global.listaClientes) {
            System.out.println(cliente.getIdFormateado() + " | " + cliente.getNombre() + " " + cliente.getApellido()
                    + " | Cuenta: " + cliente.getCuenta().getNumeroCuenta()
                    + " | Saldo: " + cliente.getCuenta().getSaldo());
        }
    }

    private static void listarCuentas() {
        for (CuentaBancaria cuenta : Global.listaCuentaBancarias) {
            System.out.println(cuenta.getNumeroCuenta() + " | Saldo: " + cuenta.getSaldo() + " | Activa: " + cuenta.isActiva());
        }
    }

    private static void listarTarjetas() {
        for (TarjetaDebito tarjeta : Global.listaTarjetas) {
            System.out.println(tarjeta.getNumeroTarjeta() + " | PIN: " + tarjeta.getPin() + " | Bloqueada: " + tarjeta.isBloqueada());
        }
    }

    private static TarjetaDebito crearTarjetaAutogenerada(String pin) {
        String numeroTarjeta = String.format("%04d %04d %04d %04d", secuenciaTarjetas, secuenciaTarjetas, secuenciaTarjetas, secuenciaTarjetas);
        secuenciaTarjetas++;
        return new TarjetaDebito(numeroTarjeta, pin, false);
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            if (!mensaje.isBlank()) {
                System.out.print(mensaje);
            }
            String entrada = leerLineaCancelable(scanner);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            if (!mensaje.isBlank()) {
                System.out.print(mensaje);
            }
            String entrada = leerLineaCancelable(scanner);
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                mostrarError("Ingrese un monto valido.");
            }
        }
    }

    private static double leerSaldoInicial(Scanner scanner, CuentaValidator cuentaValidator) {
        while (true) {
            double saldo = leerDouble(scanner, "Saldo inicial: ");
            try {
                cuentaValidator.validarSaldoInicial(saldo);
                return saldo;
            } catch (Exception e) {
                mostrarError(e.getMessage());
            }
        }
    }

    private static String leerTextoObligatorio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = leerLineaCancelable(scanner);
            if (!entrada.isBlank()) {
                return entrada;
            }
            mostrarError("El campo es obligatorio.");
        }
    }

    private static String leerNumeroCuenta(Scanner scanner) {
        while (true) {
            String numeroCuenta = leerTextoObligatorio(scanner, "Numero de cuenta: ");
            String normalizado = numeroCuenta.replace(" ", "").trim();
            if (normalizado.matches("^\\d{1,12}$")) {
                return normalizado;
            }
            mostrarError("El numero de cuenta debe tener solo digitos y maximo 12 caracteres.");
        }
    }

    private static String leerPin(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String pin = leerLineaCancelable(scanner);
            if (pin.matches("^\\d{4}$")) {
                return pin;
            }
            mostrarError("El pin debe tener exactamente 4 digitos.");
        }
    }

    private static void mostrarError(String mensaje) {
        System.out.println();
        System.out.println("Error: " + mensaje);
        System.out.println();
    }

    private static MetodoPagoTransaccion leerMetodoPago(Scanner scanner) {
        while (true) {
            System.out.println("Metodo de pago:");
            System.out.println("1. Efectivo");
            System.out.println("2. Tarjeta de debito");
            int opcion = leerEntero(scanner, "Seleccione metodo: ");
            if (opcion == 1) {
                return MetodoPagoTransaccion.EFECTIVO;
            }
            if (opcion == 2) {
                return MetodoPagoTransaccion.TARJETA_DEBITO;
            }
            mostrarError("Metodo invalido.");
        }
    }

    private static String leerLineaCancelable(Scanner scanner) {
        String entrada = scanner.nextLine().trim();
        if (entrada.equalsIgnoreCase("exit")) {
            throw new AccionCanceladaException();
        }
        return entrada;
    }

    private static void mostrarInstruccionCancelacion() {
        System.out.println("(Escribe 'exit' para cancelar)");
        System.out.println();
    }
}
