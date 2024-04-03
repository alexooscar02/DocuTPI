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
@LocalBean
@Stateless
public class Saludo implements Serializable {

    private String mensaje;

    public Saludo(String mensaje) {
        this.mensaje = mensaje;
    }

    // Otros métodos y propiedades según sea necesario
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}