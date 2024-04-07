/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest;

/**
 *
 * @author alexo
 */
public class Constantes {
    public static final String NOMBRE_PU="documientosIT-PU";
    public static final String DB_USER="postgres";
    public static final String DB_PASSWORD="123";
    public static final String DB_NAME="documentostpi135";
    public static final String DB_PORT="5432";
    //public static final String DB_URL="jdbc:tc:postgresql:9.6.8:///delivery";
    public static final String PATH_WAR="target/Documientos-1.0-SNAPSHOT.war";
    public static final String SCRIT_INIT_DB="documientos.sql";
    public static final String IMAGE_POSTGRES="postgres:13-alpine";
    public static final String IMAGE_PAYARA="payara/server-full:6.2024.1-jdk17";
    public static final String PAYARA_SERVER_FULL_LOG=".*JMXStartupService has started JMXConnector on JMXService.*";
    //public static final String PAYARA_MICRO_LOG=".* Payara Micro .* ready in .*\\s";
    public static final String PATH_TO_PAYARA_SERVER_WAR="/opt/payara/deployments/aplicacion.war";
    public static final String IMAGE_DOCUMIENTOS_SERVER="payara/full_pru4:6.2024.1";
    
}
