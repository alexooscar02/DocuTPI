/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 *
 * @author alexo
 */
@ApplicationPath("")
@ApplicationScoped
@DataSourceDefinition(
        name = "java:global/DocumientosDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "postgres",
        password = "123",
        properties = {
            "allowPublicKeyRetrieval=true",
            "useSSL=false",
            "requireSSL=false"
        }
)

public class AppConfiguration extends Application {

}
