package com.cajero.automatico.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("loginBean") // Corregido: 'l' minúscula y 'B' mayúscula
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cuentaIngresada;
    private String pinIngresado;

    public String ingresar() {
        // Validación hardcoded
        if ("1001".equals(cuentaIngresada) && "1234".equals(pinIngresado)) {
            return "menu?faces-redirect=true";
        }

        // Mensaje de error si falla
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "ERROR DE ACCESO", "Cuenta o PIN incorrectos"));

        return null;
    }

    // Getters y Setters
    public String getCuentaIngresada() { return cuentaIngresada; }
    public void setCuentaIngresada(String cuentaIngresada) { this.cuentaIngresada = cuentaIngresada; }
    public String getPinIngresado() { return pinIngresado; }
    public void setPinIngresado(String pinIngresado) { this.pinIngresado = pinIngresado; }
}