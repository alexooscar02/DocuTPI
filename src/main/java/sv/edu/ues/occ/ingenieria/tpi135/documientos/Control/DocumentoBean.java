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
import java.util.List;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Documento;

/**
 *
 * @author alexo
 */
@Stateless
@LocalBean
public class DocumentoBean extends AbstractDataAccess<Documento> implements Serializable {

    @PersistenceContext(unitName = "documientos-PU")
    EntityManager em;

    public DocumentoBean() {
        super(Documento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public Long generarNuevoIdDocumento() {
        // Consulta JPQL para obtener el último ID de documento
        Query query = em.createQuery("SELECT MAX(d.idDocumento) FROM Documento d");

        // Ejecutar la consulta y obtener el resultado
        List<Long> resultList = query.getResultList();
        Long ultimoId = resultList.get(0);

        // Retornar el nuevo ID (el último ID incrementado en 1)
        return ultimoId + 1;
    }

}
