/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.TipoAtributoBean;

/**
 *
 * @author alexo
 */
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TipoAtributoBeanIT {

    protected EntityManagerFactory emf;

    @Container
    static GenericContainer postgres = new PostgreSQLContainer("postgres:13-alpine")
            .withDatabaseName("documentostpi135")
            .withPassword("123")
            .withUsername("postgres")
            .withInitScript("documientos.sql")
            .withExposedPorts(5432);

    @BeforeAll
    public void inicializar() {
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://localhost:%d/documentostpi135", postgres.getMappedPort(5432)));
        emf = Persistence.createEntityManagerFactory("documientosIT-PU", propiedades);
    }

    @Test
    @Order(1)
    public void testContar() {
        System.out.println("contarIT");

        EntityManager em = emf.createEntityManager();
        TipoAtributoBean cut = new TipoAtributoBean();
        cut.em = em;
        int esperado = 2;
        Long resultado = cut.count();

        Assertions.assertEquals(esperado, resultado.intValue());

    }

    @Test
    @Order(2)
    public void testInsertar() {

    }

}
