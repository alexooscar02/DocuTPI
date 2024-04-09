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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.AtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.DocumentoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.MetadatoBean;
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
public class MetadatoResource implements Serializable {

    @Inject
    MetadatoBean mBean;

    @Inject
    DocumentoBean dBean;

    @Inject
    AtributoBean aBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMetadato(@PathParam("idDocumento") Long idDocumento, Metadato metadato, @Context UriInfo uriInfo) {
        if (metadato == null || metadato.getIdAtributo() == null || metadato.getIdDocumento() == null || metadato.getValor() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }

        try {
            metadato.setIdDocumento(new Documento(idDocumento));

            if (!validateAttributeBelongsToDocument(metadato.getIdAtributo(), idDocumento)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "El atributo no pertenece a la taxonomía del documento")
                        .build();
            }
            mBean.create(metadato);

            URI requestUri = uriInfo.getRequestUri();
            String location = requestUri.toString() + "/" + metadato.getIdMetadata();

            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .build();
        } catch (Exception ex) {
            // En caso de que ocurra una excepción durante la creación del metadato
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Error interno del servidor")
                    .build();
        }
    }

    private boolean validateAttributeBelongsToDocument(Atributo atributo, Long idDocumento) {
        // Obtener el documento
        Documento documento = dBean.findById(idDocumento);

        // Verificar si el documento tiene una taxonomía
        if (documento.getTaxonomiaList() == null || documento.getTaxonomiaList().isEmpty()) {
            return false;
        }

        // Obtener el TipoDocumento del primer elemento de la lista de taxonomías
        Taxonomia firstTaxonomia = documento.getTaxonomiaList().get(0);
        TipoDocumento tipoDocumento = firstTaxonomia.getIdTipoDocumento();

        // Verificar si el TipoDocumento tiene el atributo especificado
        List<Atributo> atributosDelTipoDeDocumento = aBean.findByIdTipoDocumentoIdTipoDocumento(tipoDocumento.getIdTipoDocumento());
        return atributosDelTipoDeDocumento.stream()
                .map(Atributo::getIdAtributo)
                .anyMatch(id -> id.equals(atributo.getIdAtributo()));
    }

}
