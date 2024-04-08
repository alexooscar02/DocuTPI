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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.DocumentoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Documento;

/**
 *
 * @author alexo
 */
@Path("documento")
public class DocumentoResource implements Serializable {

    @Inject
    DocumentoBean dBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Documento> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return dBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Integer idDocumento) {
        if (idDocumento != null) {
            Documento found = dBean.findById(idDocumento);
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
    public Response createDocumento(Documento documento, @Context UriInfo info) {
        // Verifica si los parámetros requeridos son nulos
        if (documento == null || documento.getNombre() == null || documento.getCreadoPor() == null || documento.getUbicacionFisica() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }

        // Lógica para crear el documento en la base de datos
        try {
            // Supongamos que se ha creado correctamente y obtenemos el ID generado
            Long idGenerado = 1L;

            // Se construye la URI del recurso creado
            URI locationUri = info.getRequestUriBuilder()
                    .path(Long.toString(idGenerado))
                    .build();

            // Se retorna una respuesta exitosa con el código 201 y la ubicación del recurso creado
            return Response.created(locationUri).build();
        } catch (Exception ex) {
            // En caso de que ocurra una excepción durante la creación del documento
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.serverError().build();
        }
    }

}
