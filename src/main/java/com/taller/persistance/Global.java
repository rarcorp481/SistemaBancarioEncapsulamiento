package com.taller.persistance;

import com.taller.models.Cliente;
import com.taller.models.CuentaBancaria;
import com.taller.models.TarjetaDebito;
import com.taller.models.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class Global {

    public static List<Cliente> listaClientes = new ArrayList<>();
    public static List<CuentaBancaria> listaCuentaBancarias = new ArrayList<>();
    public static List<TarjetaDebito> listaTarjetas = new ArrayList<>();
    public static List<Transaccion> listaTransacciones = new ArrayList<>();
}
