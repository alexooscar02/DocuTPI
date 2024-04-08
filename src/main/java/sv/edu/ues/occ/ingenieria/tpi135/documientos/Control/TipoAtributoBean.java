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

    public Integer obtenerNuevoId() {
        // Consulta para obtener el máximo ID de TipoAtributo
        Query query = em.createQuery("SELECT MAX(t.idTipoAtributo) FROM TipoAtributo t");
        Integer maxId = (Integer) query.getSingleResult();

        // Si no hay registros en la tabla, asignamos el primer ID como 1
        if (maxId == null) {
            return 1;
        }

        // Devolvemos el máximo ID incrementado en 1 para garantizar un nuevo ID único
        return maxId + 1;
    }

}
