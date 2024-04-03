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
import java.util.Collections;
import java.util.List;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoAtributo;

/**
 *
 * @author home
 */
@Stateless
@LocalBean

public class TipoAtributoBean implements Serializable {

    @PersistenceContext(unitName = "Docu_PU")
    EntityManager em;

      public List<TipoAtributo> findRange(int first, int pageSize) {
        if (first >= 0 && pageSize > 0) {
            
        }
        
        return Collections.EMPTY_LIST;
    }
    
    
    
}
