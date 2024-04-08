/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.AtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.Atributo;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoDocumento;

/**
 *
 * @author alexo
 */
@Path("atributo")
public class AtributoResource implements Serializable {

    @Inject
    AtributoBean aBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Atributo> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "pagesize")
            @DefaultValue(value = "50") int pageSize
    ) {
        if (first >= 0 && pageSize > 0) {
            return aBean.findRange(first, pageSize);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findById(@PathParam("id") final Integer idAtributo) {
        if (idAtributo != null) {
            Atributo found = aBean.findById(idAtributo);
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

    @PersistenceContext
    private EntityManager entityManager;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAtributo(Atributo atributo, @Context UriInfo info) {
        if (atributo != null && atributo.getNombre() != null
                && atributo.getNombrePantalla() != null && atributo.getObligatorio() != null) {
            try {
                // Si el id llega como null, asignamos uno nuevo
                if (atributo.getIdAtributo() == null) {
                    // Aquí implementamos la lógica para obtener un id único
                    Long nuevoId = generarNuevoIdAtributo(); // Método que devuelve un nuevo id único
                    atributo.setIdAtributo(nuevoId);
                }

                // Lógica para crear el atributo en la base de datos
                entityManager.persist(atributo);

                // Construimos la URI del recurso creado
                URI requestUri = info.getRequestUri();
                String location = requestUri.toString() + "/" + atributo.getIdAtributo();

                // Retornamos una respuesta exitosa con el código 201 y la ubicación del recurso creado
                return Response.status(Response.Status.CREATED)
                        .header("Location", location)
                        .build();
            } catch (Exception ex) {
                // En caso de que ocurra una excepción durante la creación del atributo
                ex.printStackTrace();
                return Response.serverError().build();
            }
        } else {
            // En caso de que falten parámetros en el payload
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Detalle", "Faltan parámetros en el payload")
                    .build();
        }
    }

    // Método para generar un nuevo ID único para un atributo
    private Long generarNuevoIdAtributo() {
        Query query = entityManager.createQuery("SELECT MAX(a.idAtributo) FROM Atributo a");
        Long ultimoId = (Long) query.getSingleResult();
        if (ultimoId == null) {
            return 1L; // Si no hay ningún ID en la base de datos, empezamos desde 1
        } else {
            return ultimoId + 1; // Generamos un nuevo ID sumando 1 al último ID utilizado
        }
    }
}
