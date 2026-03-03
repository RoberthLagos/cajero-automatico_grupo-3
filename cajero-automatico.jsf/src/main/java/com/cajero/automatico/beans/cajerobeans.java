package com.cajero.automatico.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.*;
import java.util.*;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("cajerobeans") 
@SessionScoped
public class cajerobeans implements Serializable {

    private static final long serialVersionUID = 1L;

    private double montoTransaccion;
    private String pinIngresado;
    private Cliente clienteActual; 
    private List<Cliente> listaClientes = new ArrayList<>();

    @PostConstruct
    public void init() {
        cargarClientesDesdeArchivo();
    }

    private void cargarClientesDesdeArchivo() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("Cliente.txt")) {
            if (is != null) {
                Scanner scanner = new Scanner(is);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.trim().isEmpty()) {
                        String[] datos = line.split(",");
                        // CORRECCIÓN: Ahora pasamos 4 datos al constructor: Cuenta, PIN, Saldo, Nombre
                        listaClientes.add(new Cliente(datos[0], datos[1], Double.parseDouble(datos[2]), datos[3]));
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar Cliente.txt: " + e.getMessage());
        }
    }

    // --- MÉTODOS DE LÓGICA ---
    public void seleccionarMonto(double cantidad) {
        this.montoTransaccion = cantidad;
    }

    public String retirar() {
        if (clienteActual == null || !clienteActual.getPin().equals(pinIngresado)) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "PIN inválido", "Acceso denegado.");
            return null;
        }
        if (montoTransaccion <= 0) {
            mostrarMensaje(FacesMessage.SEVERITY_WARN, "Monto inválido", "Ingrese un valor mayor a cero.");
            return null;
        }
        if (montoTransaccion > clienteActual.getSaldo()) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Saldo insuficiente", "Fondos no disponibles.");
            return null;
        }

        clienteActual.setSaldo(clienteActual.getSaldo() - montoTransaccion);
        limpiarYNotificar("Retiro exitoso");
        return "menu?faces-redirect=true";
    }

    public String depositar() {
        if (clienteActual == null || !clienteActual.getPin().equals(pinIngresado)) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "PIN inválido", "Acceso denegado.");
            return null;
        }
        if (montoTransaccion <= 0) {
            mostrarMensaje(FacesMessage.SEVERITY_WARN, "Monto inválido", "Ingrese un valor mayor a cero.");
            return null;
        }

        clienteActual.setSaldo(clienteActual.getSaldo() + montoTransaccion);
        limpiarYNotificar("Depósito exitoso");
        return "menu?faces-redirect=true";
    }

    private void mostrarMensaje(FacesMessage.Severity severidad, String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, resumen, detalle));
    }

    private void limpiarYNotificar(String mensaje) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        mostrarMensaje(FacesMessage.SEVERITY_INFO, mensaje, "Operación realizada correctamente.");
        this.montoTransaccion = 0;
        this.pinIngresado = "";
    }

    // --- GETTERS Y SETTERS ---
    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public double getSaldoActual() {
        return (clienteActual != null) ? clienteActual.getSaldo() : 0.0;
    }

    public double getMontoTransaccion() { return montoTransaccion; }
    public void setMontoTransaccion(double montoTransaccion) { this.montoTransaccion = montoTransaccion; }
    
    public String getPinIngresado() { return pinIngresado; }
    public void setPinIngresado(String pinIngresado) { this.pinIngresado = pinIngresado; }
    
    // Getter para acceder a la lista desde el LoginBean
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}