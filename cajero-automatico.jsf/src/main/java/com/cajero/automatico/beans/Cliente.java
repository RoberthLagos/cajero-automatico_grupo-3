package com.cajero.automatico.beans;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributos 
    private String numeroCuenta;
    private String pin;
    private double saldo;
    private String nombre; 

    // Constructor para recibir 4 parámetros
    public Cliente(String numeroCuenta, String pin, double saldo, String nombre) {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}

