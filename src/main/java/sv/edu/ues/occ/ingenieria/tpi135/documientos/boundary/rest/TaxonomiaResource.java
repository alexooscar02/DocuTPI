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
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.DocumentoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TipoDocumentoBean;

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
        if (idDocumento == null || taxonomia == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "idTipoDocumento or nuevo is null")
                    .build();
        }
        if (taxonomia.getIdDocumento() == null || taxonomia.getIdTipoDocumento() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }
        try {
            tBean.create(taxonomia);

            URI requestUri = info.getRequestUri();
            String location = requestUri.toString() + "/" + taxonomia.getIdTaxonomia();

            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .build();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Error interno del servidor")
                    .build();
        }
    }
}
