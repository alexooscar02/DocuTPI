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
import jakarta.ws.rs.core.UriBuilder;
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
    MetadatoBean mBean;

    @Inject
    DocumentoBean documentoBean;

    @Inject
    AtributoBean aBean;

    @Inject
    TaxonomiaBean tBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Metadato> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return mBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Long idMetadata) {
        if (idMetadata != null) {
            Metadato found = mBean.findById(idMetadata);
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
    public Response createMetadato(@PathParam("idDocumento") Long idDocumento, Metadato nuevoMetadato) {
        if (idDocumento == null || nuevoMetadato == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "El ID del documento está nulo")
                    .build();
        }

        if (nuevoMetadato.getIdDocumento() == null || nuevoMetadato.getIdAtributo() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "El ID del documento está nulo")
                    .build();
        }

        //boolean metadatoPertenece = mBean.verificarMetadatoPerteneceTaxonomia(idDocumento, nuevoMetadato.getIdMetadata());
        if (!Objects.equals(tBean.findByDocumento(Long.valueOf(nuevoMetadato.getIdDocumento().getIdDocumento().toString())), aBean.findTipoDocumentoById(Long.valueOf(nuevoMetadato.getIdAtributo().getIdAtributo().toString())))) {
            return Response.status(405)
                    .header("METODO-NO-POSIBLE", nuevoMetadato.toString())
                    .build();
        }

        mBean.create(nuevoMetadato);

        String location = String.format("/documento/%d/metadato/%d", idDocumento, nuevoMetadato.getIdMetadata());
        return Response.status(Response.Status.CREATED)
                .header("Location", location)
                .build();

    }
}
