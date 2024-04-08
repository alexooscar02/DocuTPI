/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TaxonomiaBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Documento;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Taxonomia;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoDocumento;

/**
 *
 * @author alexo
 */
@Path("/documento/{idDocumento}/taxonomia")
public class TaxonomiaResource implements Serializable {

    @Inject
    TaxonomiaBean tBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTaxonomia(@PathParam("idDocumento") Long idDocumento, Taxonomia taxonomia, @Context UriInfo info) {
        if (taxonomia == null || taxonomia.getIdDocumento() == null || taxonomia.getIdTipoDocumento() == null) {
            // Caso: Payload nulo o parámetros faltantes
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }

        try {
            // Asignamos el ID del documento y el ID del tipo de documento a la taxonomía
            taxonomia.setIdDocumento(new Documento(idDocumento));
            taxonomia.setIdTipoDocumento(new TipoDocumento(taxonomia.getIdTipoDocumento().getIdTipoDocumento())); // Asignar solo el ID

            // Lógica para crear la taxonomía en la base de datos
            tBean.create(taxonomia);

            // Construimos la URI del recurso creado
            URI requestUri = info.getRequestUri();
            String location = requestUri.toString() + "/" + taxonomia.getIdTaxonomia();

            // Retornamos una respuesta exitosa con el código 201 y la ubicación del recurso creado
            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .build();
        } catch (Exception ex) {
            // En caso de que ocurra una excepción durante la creación de la taxonomía
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Error interno del servidor")
                    .build();
        }

    }

}
