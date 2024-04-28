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
import static java.awt.SystemColor.info;
import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.AtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.DocumentoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.MetadatoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TaxonomiaBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Atributo;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Documento;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Metadato;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Taxonomia;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoDocumento;

/**
 *
 * @author alexo
 */
@Path("/documento/{idDocumento}/metadato")
public class MetadatoResource {

    @Inject
    MetadatoBean metadatoBean;

    @Inject
    DocumentoBean documentoBean;

    @Inject
    AtributoBean atributoBean;

    @Inject
    TaxonomiaBean taxonomiaBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMetadato(@PathParam("idDocumento") Long idDocumento, Metadato metadato, @Context UriInfo info) {
        // Verificar si el ID del documento y el objeto metadato son nulos
        // Verificar que se proporcionó un documento válido
        if (idDocumento == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de documento inválido")
                    .build();
        }

        // Verificar que se proporcionó un metadato válido
        if (metadato == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Metadato inválido")
                    .build();
        }

        // Verificar que el documento existe
        Documento documento = documentoBean.findById(idDocumento);
        if (documento == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Documento no encontrado")
                    .build();
        }

        // Verificar que el atributo del metadato existe
        Atributo atributo = atributoBean.findById(metadato.getIdAtributo());
        if (atributo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atributo no encontrado")
                    .build();
        }

        // Verificar que el atributo pertenece al documento
        Taxonomia taxonomia = taxonomiaBean.findByDocumentoAndAtributo(idDocumento, metadato.getIdAtributo().getIdAtributo());
        if (taxonomia == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El atributo no pertenece al documento")
                    .build();
        }
        try {
            metadato.setIdDocumento(documento);
            metadatoBean.create(metadato);

            // Construir la URI de la respuesta y devolver la respuesta
            URI requestUri = info.getRequestUri();
            String location = requestUri.toString() + "/" + metadato.getIdMetadata();
            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .entity("Metadato creado exitosamente")
                    .build();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            // En caso de error interno del servidor, devolver una respuesta con el estado 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Error interno del servidor")
                    .build();
        }

        // Crear el metadato
    }
}
