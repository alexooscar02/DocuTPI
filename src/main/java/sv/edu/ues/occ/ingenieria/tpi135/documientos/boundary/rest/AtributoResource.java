/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.AtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Atributo;

/**
 *
 * @author alexo
 */
@Path("atributo")
public class AtributoResource implements Serializable {

    @Inject
    AtributoBean aBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Atributo> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return aBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Integer idAtributo) {
        if (idAtributo != null) {
            Atributo found = aBean.findById(idAtributo);
            if (found != null) {
                return Response.status(Response.Status.OK)
                        .entity(found)
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .header("not-found", "id")
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Atributo atributo, @Context UriInfo info) {
        if (atributo != null && atributo.getIdAtributo() != null && atributo.getNombre() != null) {
            try {
                aBean.create(atributo);
                URI requestUri = info.getRequestUri();
                return Response.status(Response.Status.CREATED)
                        .header("location", requestUri.toString() + "/" + atributo.getIdAtributo())
                        .build();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            return Response.status(500)
                    .header("create-exception", atributo.toString())
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .build();
    }
}
