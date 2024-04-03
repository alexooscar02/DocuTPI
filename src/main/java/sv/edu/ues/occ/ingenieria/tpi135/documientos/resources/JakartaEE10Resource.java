package sv.edu.ues.occ.ingenieria.tpi135.documientos.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi135.documientos.Control.SaludoBean;


@Path("jakartaee10")
public class JakartaEE10Resource {

   public 
           SaludoBean sBean;
   
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
     public String Saludaringles(String nombre) {
        if (nombre !=null){
            return "hello" +nombre; 
        }
        return "hello";
    }
    
}
