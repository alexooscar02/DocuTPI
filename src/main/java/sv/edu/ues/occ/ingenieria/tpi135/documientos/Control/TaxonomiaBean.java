/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.Control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
    //aqui hace falta un metodo que busque tipoDocumento por documento
    public Long findTipoDocumentoByDocumento(Long idDocumento) {
        try {
            Query query = em.createNamedQuery("Taxonomia.findTipoDocumentoByDocumento");
            query.setParameter("idDocumento", idDocumento);
            List<Long> results = query.getResultList();
            if (!results.isEmpty()) {
                return results.get(0);
            } else {
                return null; // O maneja adecuadamente el caso cuando no se encuentra ningún resultado
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public List<Taxonomia> findByDocumento(Long idDocumento) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Taxonomia> cq = cb.createQuery(Taxonomia.class);
            Root<Taxonomia> root = cq.from(Taxonomia.class);

            cq.select(root).where(cb.equal(root.get("idDocumento"), idDocumento));

            return em.createQuery(cq).getResultList();
        } catch (Exception ex) {
            // Manejo de excepciones
            return Collections.emptyList(); // O devuelve null, dependiendo de tu caso de uso
        }
    }

    public Taxonomia findByDocumentoAndAtributo(Long idDocumento, Long idAtributo) {
        try {
            return em.createNamedQuery("Taxonomia.findByDocumentoAndAtributo", Taxonomia.class)
                    .setParameter("idDocumento", idDocumento)
                    .setParameter("idAtributo", idAtributo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
