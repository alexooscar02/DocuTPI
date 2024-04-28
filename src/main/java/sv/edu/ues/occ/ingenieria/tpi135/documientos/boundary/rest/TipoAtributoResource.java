/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Integer idTipoAtributo) {
        if (idTipoAtributo != null) {
            TipoAtributo found = taBean.findById(idTipoAtributo);
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
    public Response createTipoAtributo(TipoAtributo nuevoTipoAtributo, @Context UriInfo uriInfo) {
        if (nuevoTipoAtributo == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros nulos")
                    .build();
        }
        if (!isTipoAtributoValid(nuevoTipoAtributo)) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }

        try {
            taBean.create(nuevoTipoAtributo);

            URI requestUri = uriInfo.getRequestUri();
            String location = requestUri.toString() + "/" + nuevoTipoAtributo.getIdTipoAtributo();

            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .build();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            URI location = URI.create("/tipoatributo/" + nuevoTipoAtributo.getIdTipoAtributo());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Error interno del servidor")
                    .build();
        }

    }

    private boolean isTipoAtributoValid(TipoAtributo tipoAtributo) {
        return tipoAtributo.getNombre() != null
                && tipoAtributo.getExpresionRegular() != null
                && tipoAtributo.getIndicacionesScreen() != null
                && tipoAtributo.getNombreScreen() != null
                && tipoAtributo.getObservaciones() != null;
    }

}
