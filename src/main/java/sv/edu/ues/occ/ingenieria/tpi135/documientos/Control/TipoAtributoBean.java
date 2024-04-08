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
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoAtributo;

/**
 *
 * @author home
 */
@Stateless
@LocalBean
public class TipoAtributoBean extends AbstractDataAccess<TipoAtributo> implements Serializable {

    @PersistenceContext(unitName = "documientos-PU")
    public EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public TipoAtributoBean() {
        super(TipoAtributo.class);
    }

    public void crear(TipoAtributo registro) throws IllegalStateException, IllegalArgumentException {

        try {
            if (em != null) {
                em.persist(registro);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        throw new IllegalStateException();

    }

}
