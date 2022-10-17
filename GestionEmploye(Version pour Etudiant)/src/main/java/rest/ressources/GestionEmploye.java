package rest.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import rest.Filter.Secured;
import rest.entities.Employe;

@Secured
@Api
@Path("employes")
public class GestionEmploye {
	
	public static  List<Employe> employes=new ArrayList<Employe>();
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String ajouterEmploye(Employe employe) {
		 if(employes.add(employe))
	 return "Add Successful";
		return "Echec";
	}

	@Path("hello")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public  String  Sayhello() {
			return "hello";			
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public  Response  displayEmployeesList() {
		
		if(employes.size()!=0)
			return Response.status(Status.FOUND).entity(employes).build();
		else
			return Response.status(Status.NOT_FOUND).build();
					
	}
	
	@GET
	@Path("{cin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmploye(@PathParam(value="cin")int cin) {  // j'ai ajouter @PathParam et je peut utilise @@QueryParam 
		for (Employe info:employes) {
	       if(info.getCin()==cin) {
	    	   return  Response.status(Status.FOUND)
						.entity(info)
						.build(); 
	    	
	       }
		}
	       		
			return  Response.status(Status.NOT_FOUND).build();
		
		
	}
	
		
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateEmploye(Employe e) {
		int index= this.getIndexByCin(e.getCin());
		if (index!=-1) {
			employes.set(index, e);
			return Response.status(Status.OK).entity("update successful").build();
			
		}
		return Response.status(Status.NOT_FOUND).entity(e).build();
	
	}
	
	@DELETE
	@Path("{cin}")
	 @Produces(MediaType.TEXT_PLAIN)
	public Response deleteEmpl(@PathParam(value="cin")int cin){
		int index= getIndexByCin(cin);
		
		if (index!=-1) {
			employes.remove(index);
			return Response.status(Status.OK).entity(true).build();
		}else 
			return Response.status(Status.NOT_FOUND).build();
			
    }
	
	public int getIndexByCin(int cin) {
		for(Employe emp: employes) {
			if (emp.getCin()==cin)
				return employes.indexOf(emp);
		}
		return -1;
	}
	
		
}
