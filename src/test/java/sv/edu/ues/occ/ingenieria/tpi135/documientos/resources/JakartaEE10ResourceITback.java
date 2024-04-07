package sv.edu.ues.occ.ingenieria.tpi135.documientos.resources;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Order;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

/**
 *
 * @author home
 */
@Testcontainers
public class JakartaEE10ResourceITback {

    MountableFile war = MountableFile.forHostPath(Paths.get("target/Documientos-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);

    @Container
    GenericContainer payara = new GenericContainer("payara/server-full:6.2024.1-jdk17")
            .withCopyFileToContainer(war, "/opt/payara/deployments/aplicacion.war")
            .waitingFor(Wait.forLogMessage(".*deploy AdminCommandApplication deployed with name aplicacion.*", 1))
            .withExposedPorts(8080);

    @Test
    @Order(1)
    public void testPing() throws Exception {
        System.out.println("pingIntegracion");
        //http://localhost:8080/aplicacion/resources/jakartaee10

        //        fail("The test case is a prototype.");
        Assertions.assertTrue(payara.isRunning());
        int esperado = 200;

        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(String.format("http://localhost:%d/aplicacion/resources/jakartaee10", payara.getMappedPort(8080))))
                .GET()
                .build();

        HttpClient cliente = HttpClient.newBuilder().build();

        HttpResponse<String> resp = cliente.send(req, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(esperado, resp.statusCode());
        System.out.println(resp.body());
    }

}
