/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TipoAtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoAtributo;

/**
 *
 * @author home
 */
@Path("tipoatributo")
public class TipoAtributoResource implements Serializable {

    @Inject
    TipoAtributoBean taBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoAtributo> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return taBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }
}
