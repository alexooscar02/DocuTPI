/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

import java.util.List;
import java.util.Arrays;
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

    static List<TipoAtributo> LISTA_TIPO = Arrays.asList(new TipoAtributo[]{
        new TipoAtributo(1),
        new TipoAtributo(2),
        new TipoAtributo(3),}
    );

    @Test
    public void testFindRange() {
        System.out.println("findRange");
        final int desde = 0;
        final int hasta = 50;
   TipoAtributoBean  mockTAB=Mockito.mock(TipoAtributoBean.class);
   Mockito.when(mockTAB.findRange(desde,hasta)).thenReturn(LISTA_TIPO);
   
   TipoAtributoResource  cut=new TipoAtributoResource();
   cut.taBean=mockTAB;
   List<TipoAtributo> resultado=cut.findRange(desde,hasta);
        Assertions.assertEquals(LISTA_TIPO.size(),resultado.size());
        
   
//        Assertions.fail("esta prueba no pasa ");

    }

}
