package com.cajero.automatico.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.*;
import java.util.*;
import java.io.Serializable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cuentaIngresada;
    private String pinIngresado;

    @Inject
    private cajerobeans cajero;

    public String ingresar() {
        // 1. Cargar la lista de clientes desde el archivo
        List<Cliente> clientes = cargarClientes();
        
        // 2. Buscar si existe un cliente con esa cuenta y PIN
        for (Cliente c : clientes) {
            if (c.getNumeroCuenta().equals(cuentaIngresada) && c.getPin().equals(pinIngresado)) {
                // 3. Si lo encuentra, se lo asignamos al Bean del cajero
                cajero.setClienteActual(c); 
                return "menu?faces-redirect=true";
            }
        }

        // 4. Si sale del ciclo, es que no lo encontró
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "ACCESO DENEGADO", "Cuenta o PIN incorrectos"));
        return null;
    }

    private List<Cliente> cargarClientes() {
        List<Cliente> lista = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("Cliente.txt")) {
            if (is != null) {
                Scanner scanner = new Scanner(is);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.trim().isEmpty()) {
                        String[] datos = line.split(",");
                        // Constructor recibe: Cuenta, PIN, Saldo, Nombre
                        lista.add(new Cliente(datos[0], datos[1], Double.parseDouble(datos[2]), datos[3]));
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            System.err.println("Error en LoginBean al cargar clientes: " + e.getMessage());
        }
        return lista;
    }

    // Getters y Setters
    public String getCuentaIngresada() { return cuentaIngresada; }
    public void setCuentaIngresada(String cuentaIngresada) { this.cuentaIngresada = cuentaIngresada; }
    public String getPinIngresado() { return pinIngresado; }
    public void setPinIngresado(String pinIngresado) { this.pinIngresado = pinIngresado; }
}