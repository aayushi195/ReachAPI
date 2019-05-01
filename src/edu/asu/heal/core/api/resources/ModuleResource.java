package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.responses.ModuleResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleActivityList;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleInstance;
import edu.asu.heal.reachv3.api.service.ReachService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/modules")
public class ModuleResource {

	@Context
	private UriInfo _uri;

	private HealService reachService = HealServiceFactory.getTheService();

	@GET
	@Path("/progression")
	@Produces("application/hal+json")
	public Response getMovementOfClouds(@QueryParam("patientPin") int patientPin) {

		HEALResponse response;
		HEALResponseBuilder builder;

		ReachService service = (ReachService) reachService;
		ModuleInstance moduleInstance;
		try {
			builder = new HEALResponseBuilder(ModuleResponse.class);
		} catch (InstantiationException | IllegalAccessException ie) {
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		if (patientPin == 0 || patientPin < -1) {
			response = builder
					.setData("YOUR PATIENT PIN IS ABSENT FROM THE REQUEST")
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setServerURI(_uri.getBaseUri().toString())
					.build();
		} else {
			moduleInstance = service.getScheduleOfModules(patientPin);
			if (moduleInstance == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(moduleInstance)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		}
		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}
	
	@GET
	@Path("/{module}")
	@Produces("application/hal+json")
	public Response getActivityListWithCallToAction(@PathParam("module") String module,
			@QueryParam("patientPin") int patientPin) {

		HEALResponse response;
		HEALResponseBuilder builder;

		ReachService service = (ReachService) reachService;
		ModuleActivityList moduleInstance = null;
		try {
			builder = new HEALResponseBuilder(ModuleResponse.class);
		} catch (InstantiationException | IllegalAccessException ie) {
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		if (patientPin == 0 || patientPin < -1 || Integer.parseInt(module) < 1 || Integer.parseInt(module) > 6) {
			response = builder
					.setData("ISSUE WITH PATIENT PIN AND/OR MODULE IN THE REQUEST")
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setServerURI(_uri.getBaseUri().toString())
					.build();
		} else {
			moduleInstance = service.getActivityListWithCallToAction(module, patientPin);
			if (moduleInstance == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(moduleInstance)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		}
		return Response.status(Response.Status.OK.getStatusCode()).entity(moduleInstance).build();
	}
	
}
