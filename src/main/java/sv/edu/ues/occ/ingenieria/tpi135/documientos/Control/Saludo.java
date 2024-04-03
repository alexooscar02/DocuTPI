/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.Control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.io.Serializable;

/**
 *
 * @author home
 */
@Stateless
public class Saludo implements Serializable {

    private String mensaje;

    // Constructor sin argumentos requerido por CDI
    public Saludo() {
        // Puedes inicializar el mensaje con un valor predeterminado si es necesario
        this.mensaje = "";
    }

    public Saludo(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getters y setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}