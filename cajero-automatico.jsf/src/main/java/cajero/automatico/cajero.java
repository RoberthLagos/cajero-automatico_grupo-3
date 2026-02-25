package cajero.automatico;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named(value = "cajeroBean") // Este nombre se usa en el index.xhtml
@SessionScoped               // Mantiene el saldo mientras no cierres el navegador
public class cajero implements Serializable {

    private double saldo = 500.0; // Saldo inicial de prueba
    private double monto;

    public void depositar() {
        this.saldo += this.monto;
        this.monto = 0; // Limpiar el campo
    }

    public void retirar() {
        if (this.monto <= this.saldo) {
            this.saldo -= this.monto;
        }
        this.monto = 0;
    }

    // Getters y Setters obligatorios para JSF
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}