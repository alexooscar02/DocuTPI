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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
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
    public Response createTipoAtributo(TipoAtributo tipoAtributo, @Context UriInfo info) {
        if (tipoAtributo == null || tipoAtributo.getExpresionRegular() == null || tipoAtributo.getIndicacionesScreen() == null
                || tipoAtributo.getNombre() == null || tipoAtributo.getNombreScreen() == null || tipoAtributo.getObservaciones() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }

        try {
            
            taBean.create(tipoAtributo);

            URI requestUri = info.getRequestUri();
            String location = requestUri.toString() + "/" + tipoAtributo.getIdTipoAtributo();

            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .build();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.serverError().build();
        }
    }

//    public Integer obtenerNuevoId() {
//       Integer random= new Random().nextInt(9999);
//        // Generar un entero aleatorio en el rango de Integer.MIN_VALUE a Integer.MAX_VALUE
//        return random;
//    }
}

//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/{id}")
//    public Response update(@PathParam("id") Integer idTipoAtributo, TipoAtributo updatedTipoAtributo) {
//        if (idTipoAtributo != null && updatedTipoAtributo != null) {
//            TipoAtributo existingTipoAtributo = taBean.findById(idTipoAtributo);
//            if (existingTipoAtributo != null) {
//                try {
//                    existingTipoAtributo.setNombre(updatedTipoAtributo.getNombre());
//                    // Actualizar otros campos si es necesario
//                    TipoAtributo modifiedTipoAtributo = taBean.modify(existingTipoAtributo);
//                    return Response.status(Response.Status.OK)
//                            .entity(modifiedTipoAtributo)
//                            .build();
//                } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                    return Response.status(500)
//                            .header("update-exception", updatedTipoAtributo.toString())
//                            .build();
//                }
//            } else {
//                return Response.status(Response.Status.NOT_FOUND)
//                        .header("not-found", "id")
//                        .build();
//            }
//        }
//        return Response.status(422)
//                .header("missing-parameter", "id or updated data")
//                .build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response delete(@PathParam("id") Integer idTipoAtributo) {
//        if (idTipoAtributo != null) {
//            TipoAtributo tipoAtributoToDelete = taBean.findById(idTipoAtributo);
//            if (tipoAtributoToDelete != null) {
//                try {
//                    taBean.delete(tipoAtributoToDelete);
//                    return Response.status(Response.Status.NO_CONTENT).build();
//                } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                    return Response.status(500)
//                            .header("delete-exception", tipoAtributoToDelete.toString())
//                            .build();
//                }
//            } else {
//                return Response.status(Response.Status.NOT_FOUND)
//                        .header("not-found", "id")
//                        .build();
//            }
//        }
//        return Response.status(422)
//                .header("missing-parameter", "id")
//                .build();
//    }
//
//    @GET
//    @Path("/count")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response count() {
//        try {
//            Long total = taBean.count();
//            return Response.status(Response.Status.OK)
//                    .entity(total)
//                    .build();
//        } catch (Exception ex) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            return Response.status(500)
//                    .header("count-exception", ex.getMessage())
//                    .build();
//        }
//    }

