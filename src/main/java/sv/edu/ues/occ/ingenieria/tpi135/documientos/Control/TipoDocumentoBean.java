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
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoDocumento;

/**
 *
 * @author alexo
 */
@Stateless
@LocalBean
public class TipoDocumentoBean extends AbstractDataAccess<TipoDocumento> implements Serializable {

    @PersistenceContext(unitName = "documientos-PU")
    public EntityManager em;

    public TipoDocumentoBean() {
        super(TipoDocumento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Integer obtenerNuevoId() {
        // Consulta para obtener el máximo ID de TipoAtributo
        Query query = em.createQuery("SELECT MAX(t.idTipoDocumento) FROM TipoDocumento t");
        Integer maxId = (Integer) query.getSingleResult();

        // Si no hay registros en la tabla, asignamos el primer ID como 1
        if (maxId == null) {
            return 1;
        }

        // Devolvemos el máximo ID incrementado en 1 para garantizar un nuevo ID único
        return maxId + 1;
    }

}
