/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.Control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Taxonomia;

/**
 *
 * @author alexo
 */
@Stateless
@LocalBean
public class TaxonomiaBean extends AbstractDataAccess<Taxonomia> implements Serializable {

    @PersistenceContext(unitName = "documientos-PU")
    EntityManager em;

    public TaxonomiaBean() {
        super(Taxonomia.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
//    public void create2(Taxonomia taxonomia) {
//        try {
//            EntityTransaction tx = em.getTransaction();
//            tx.begin();
//            em.persist(taxonomia);
//            tx.commit();
//        } catch (Exception ex) {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//            throw new IllegalStateException("Error al crear la taxonomía", ex);
//        }
//    }
}
