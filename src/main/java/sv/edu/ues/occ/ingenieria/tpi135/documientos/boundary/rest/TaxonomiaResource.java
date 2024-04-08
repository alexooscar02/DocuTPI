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
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TaxonomiaBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Taxonomia;

/**
 *
 * @author alexo
 */
@Path("taxonomia")
public class TaxonomiaResource implements Serializable {

    @Inject
    TaxonomiaBean tBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Taxonomia> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return tBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Integer idTaxonomia) {
        if (idTaxonomia != null) {
            Taxonomia found = tBean.findById(idTaxonomia);
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
    public Response createTaxonomia(Taxonomia taxonomia, @Context UriInfo info) {
        if (taxonomia != null && taxonomia.getIdDocumento() != null && taxonomia.getIdTipoDocumento() != null) {
            try {
                // Lógica para crear la taxonomía en la base de datos
                // Aquí deberías tener tu lógica para persistir la nueva taxonomía
                // Supondré que tienes un método create en algún EJB para persistirla
                // algo así como taxonomiaBean.create(taxonomia);

                // Supongamos que se ha creado correctamente y obtenemos el ID generado
                // Aquí debes obtener el ID generado por la base de datos después de persistir la Taxonomia
                // Lo simularé aquí para completar el ejemplo
                Long idGenerado = 1L;

                // Se construye la URI del recurso creado
                URI requestUri = info.getRequestUri();
                String location = requestUri.toString() + "/" + idGenerado;

                // Se retorna una respuesta exitosa con el código 201 y la ubicación del recurso creado
                return Response.status(Response.Status.CREATED)
                        .header("Location", location)
                        .build();
            } catch (Exception ex) {
                // En caso de que ocurra una excepción durante la creación de la taxonomía
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                return Response.serverError().build();
            }
        } else {
            // En caso de que falten parámetros en el payload
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Detalle", "Faltan parámetros en el payload")
                    .build();
        }
    }
}
