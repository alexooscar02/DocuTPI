/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.Control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Atributo;

/**
 *
 * @author alexo
 */
@Stateless
@LocalBean
public class AtributoBean extends AbstractDataAccess<Atributo> implements Serializable {

    @PersistenceContext(unitName = "documientos-PU")
    EntityManager em;

    public AtributoBean() {
        super(Atributo.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
