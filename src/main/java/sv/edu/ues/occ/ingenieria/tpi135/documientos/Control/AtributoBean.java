/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.Control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
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

    public Long obtenerNuevoId() {
        Query query = em.createQuery("SELECT MAX(a.idAtributo) FROM Atributo a");
        Long ultimoId = (Long) query.getSingleResult();
        if (ultimoId == null) {
            return 1L; // Si no hay ningún ID en la base de datos, empezamos desde 1
        } else {
            return ultimoId + 1; // Generamos un nuevo ID sumando 1 al último ID utilizado
        }
    }

    public List<Atributo> findByIdTipoDocumentoIdTipoDocumento(Integer idTipoDocumento) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Atributo> query = cb.createQuery(Atributo.class);
        Root<Atributo> root = query.from(Atributo.class);
        query.where(cb.equal(root.get("idTipoDocumento").get("idTipoDocumento"), idTipoDocumento));
        return em.createQuery(query).getResultList();
    }

}
