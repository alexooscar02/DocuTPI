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
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.AtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Atributo;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoDocumento;

/**
 *
 * @author alexo
 */
@Path("/tipodocumento/{idTipoDocumento}/atributo")
public class AtributoResource implements Serializable {

    @Inject
    AtributoBean aBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAtributo(@PathParam("idTipoDocumento") Integer idTipoDocumento, Atributo atributo, @Context UriInfo info) {
        if (atributo == null || atributo.getNombre() == null || atributo.getNombrePantalla() == null || atributo.getObligatorio() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "Parámetros incorrectos")
                    .build();
        }
        try {
            atributo.setIdTipoDocumento(new TipoDocumento(idTipoDocumento));
            
            if (atributo.getIdAtributo()== null) {
                Long nuevoId = aBean.obtenerNuevoId(); // Método que devuelve un nuevo id único
                atributo.setIdAtributo(nuevoId);
            }
            aBean.create(atributo);

            URI requestUri = info.getRequestUri();
            String location = requestUri.toString() + "/" + atributo.getIdAtributo();

            return Response.status(Response.Status.CREATED)
                    .header("Location", location)
                    .build();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.serverError().build();
        }
    }

}
