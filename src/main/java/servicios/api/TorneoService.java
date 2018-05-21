package servicios.api;

import java.lang.reflect.Type;
import javax.ws.rs.core.MediaType;
import java.util.List;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entidades.Torneo;
import persistencia.TorneoPersistence;

@Path("/torneos")

public class TorneoService {
	
	@EJB TorneoPersistence beanTorneo;
	
	@GET
    @Path("/agregar/{nombre}")
	@Produces({MediaType.TEXT_PLAIN})
    public String agregarTorneo(@PathParam("nombre") String nombre) {
        String mensaje;
        if(beanTorneo.agregarTorneo(nombre)) {
        	mensaje = "El torneo Se ha agregado Correctamente";
        }else {
        	mensaje = "ERROR: El torneo no se pudo persistir";
        }
        return mensaje;
    }
	
	@GET
    @Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
    public String getTorneo(@PathParam("id") int id) {
		Gson g = new Gson();
        return g.toJson(beanTorneo.getTorneo(id));
    }
	
	@GET
    @Path("/hello")
	@Produces({MediaType.APPLICATION_XML})
    public String sayHello(){
		System.out.println("entre al get hello");
        return "Hola pelotudos";
    }
	
	@GET
    @Path("/all")
	@Produces({MediaType.APPLICATION_JSON})
    public String getAllTorneos() {
		Type listType = new TypeToken<List<Torneo>>() {}.getType();
		List<Torneo> target = beanTorneo.getAllTorneos();
		Gson gson = new Gson();
		String json = gson.toJson(target, listType);
        return "Retorno todos los torneos "+json;
    }
	
	

}
