/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TipoAtributoBean;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.entity.TipoAtributo;

/**
 *
 * @author home
 */
public class TipoAtributoResourceTest {

    final static String ENDPOINT = "http://localhost:8080/Documientos/resources";

    final Client cliente = ClientBuilder.newClient();

    final WebTarget target = cliente.target(ENDPOINT);

    @Test
    public void testCrear() {
        System.out.println("crear");
        int status_esperado = 201;
        JsonObjectBuilder constructor = Json.createObjectBuilder();
        Integer id = new Random().nextInt(9999);
        constructor
                .add("idTipoAtributo", id)
                .add("nombre", "CR")
                .add("observaciones", "bla");

        Response respuesta = target.path("tipoatributo")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(
                        Entity.json(constructor.build().toString())
                );

        Assertions.assertEquals(status_esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("location"));
    }

//    static List<TipoAtributo> LISTA_TIPO = Arrays.asList(new TipoAtributo[]{
//        new TipoAtributo(1),
//        new TipoAtributo(2),
//        new TipoAtributo(3),}
//    );
//
//    @Test
//    public void testFindRange() {
//        System.out.println("findRange");
//        final int desde = 0;
//        final int hasta = 50;
//   TipoAtributoBean  mockTAB=Mockito.mock(TipoAtributoBean.class);
//   Mockito.when(mockTAB.findRange(desde,hasta)).thenReturn(LISTA_TIPO);
//   
//   TipoAtributoResource  cut=new TipoAtributoResource();
//   cut.taBean=mockTAB;
//   List<TipoAtributo> resultado=cut.findRange(desde,hasta);
//        Assertions.assertEquals(LISTA_TIPO.size(),resultado.size());
//        
//   
////        Assertions.fail("esta prueba no pasa ");
//
//    }
}
