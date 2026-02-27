package com.cajero.automatico.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("loginBean") 
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cuentaIngresada;
    private String pinIngresado;

    public String ingresar() {
        // Limpiamos la cuenta de guiones y espacios por si acaso
        String cuentaLimpia = cuentaIngresada.replace("-", "").replace("_", "").trim();
        
        // Ahora validamos con un número de cuenta real (16 dígitos) o el tuyo corto
        if (("1001".equals(cuentaLimpia) || "0000000000001001".equals(cuentaLimpia)) 
            && "1234".equals(pinIngresado)) {
            
            return "menu?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "ACCESO DENEGADO", "Verifique su Tarjeta y PIN"));

        return null;
    }

    // Getters y Setters
    public String getCuentaIngresada() { return cuentaIngresada; }
    public void setCuentaIngresada(String cuentaIngresada) { this.cuentaIngresada = cuentaIngresada; }
    public String getPinIngresado() { return pinIngresado; }
    public void setPinIngresado(String pinIngresado) { this.pinIngresado = pinIngresado; }
}