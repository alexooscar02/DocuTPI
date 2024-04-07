/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

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
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TipoDocumentoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoAtributo;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoDocumento;

/**
 *
 * @author alexo
 */
@Path("tipodocumento")
public class TipoDocumentoResource implements Serializable {

    @Inject
    TipoDocumentoBean tdBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoDocumento> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return tdBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Integer idTipoDocumento) {
        if (idTipoDocumento != null) {
            TipoDocumento found = tdBean.findById(idTipoDocumento);
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
    public Response create(TipoDocumento tipoDocumento, @Context UriInfo info) {
        if (tipoDocumento != null && tipoDocumento.getNombre() != null && tipoDocumento.getActivo() != null) {
            try {
                // Lógica para crear el tipo de documento en la base de datos
                // Supongamos que hay un servicio de persistencia llamado tipoDocumentoService
                tdBean.create(tipoDocumento);

                // Se construye la URI del recurso creado
                URI requestUri = info.getRequestUri();
                String location = requestUri.toString() + "/" + tipoDocumento.getIdTipoDocumento();

                // Se retorna una respuesta exitosa con el código 201 y la ubicación del recurso creado
                return Response.status(Response.Status.CREATED)
                        .header("Location", location)
                        .build();
            } catch (Exception ex) {
                // En caso de que ocurra una excepción durante la creación del tipo de documento
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                return Response.serverError().build();
            }
        } else {
            // En caso de que falten parámetros en el payload
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }
    }

}

//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/{id}")
//    public Response update(@PathParam("id") Integer idTipoDocumento, TipoDocumento updatedTipoDocumento) {
//        if (idTipoDocumento != null && updatedTipoDocumento != null) {
//            TipoDocumento existingTipoDocumento = tdBean.findById(idTipoDocumento);
//            if (existingTipoDocumento != null) {
//                try {
//                    existingTipoDocumento.setNombre(updatedTipoDocumento.getNombre());
//                    // Actualizar otros campos si es necesario
//                    TipoDocumento modifiedTipoDocumento = tdBean.modify(existingTipoDocumento);
//                    return Response.status(Response.Status.OK)
//                            .entity(modifiedTipoDocumento)
//                            .build();
//                } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                    return Response.status(500)
//                            .header("update-exception", updatedTipoDocumento.toString())
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
//    public Response delete(@PathParam("id") Integer idTipoDocumento) {
//        if (idTipoDocumento != null) {
//            TipoDocumento tipoDocumentoToDelete = tdBean.findById(idTipoDocumento);
//            if (tipoDocumentoToDelete != null) {
//                try {
//                    tdBean.delete(tipoDocumentoToDelete);
//                    return Response.status(Response.Status.NO_CONTENT).build();
//                } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                    return Response.status(500)
//                            .header("delete-exception", tipoDocumentoToDelete.toString())
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
//            Long total = tdBean.count();
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

