package sv.edu.ues.occ.ingenieria.tpi135.documientos.resources;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.Saludo;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.SaludoBean;

/**
 *
 * @author home
 */
public class JakartaEE10ResourceTest {

    public JakartaEE10ResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testPing() {
        System.out.println("ping");
        SaludoBean mockSaludoBean = Mockito.mock(SaludoBean.class);
        Mockito.when(mockSaludoBean.saludar("ping")).thenReturn(new Saludo("chepe"));

        JakartaEE10Resource instance = new JakartaEE10Resource();
        instance.sBean = mockSaludoBean;
        Response expResult = Response
                .ok("ping Jakarta EE")
                .build();

        Response result = instance.ping();
        String resultado = (String) result.getEntity();
        System.out.println("Resultado" + resultado);

        assertTrue(result.getStatus() == 200);
        //fail("The test case is a prototype.");
    }

//    @Test
//    public void testSaludarIngles() {
//        String nombre = "jose ";
//        String esperado = "hello jose ";
//        String resultado = null;
//        JakartaEE10Resource cut = new JakartaEE10Resource();
//        resultado = cut.Saludaringles(nombre);
//
//        assertNull(resultado);
//        assertEquals(esperado, resultado);
//
//    }

}
