/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.resources;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 *
 * @author home
 */
@Testcontainers
public class JakartaEE10ResourceIT {

    @Container
    GenericContainer payara = new GenericContainer("payara/server-full:6.2024.1-jdk17")
            .withExposedPorts(8080);

    @Test
    public void testPing() {

        System.out.println("pingIntegracion");
//        fail("the test case  is a prototype");
        Assertions.assertTrue(payara.isRunning());
    }

}
