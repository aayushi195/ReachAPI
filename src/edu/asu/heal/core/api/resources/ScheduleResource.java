package edu.asu.heal.core.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import edu.asu.heal.core.api.models.NullObjects;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.responses.ScheduleResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

@Path("/schedules")
public class ScheduleResource {

	@Context
	private UriInfo _uri;

	private HealService reachService = HealServiceFactory.getTheService();

	@POST
	@Consumes("application/json")
	public Response createPatientSchedule(String patientJson) {
		HEALResponse response = null;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ScheduleResponse.class);

			JSONObject patientData = new JSONObject(patientJson);
			int patientPin = -1;
			if(patientData.has("patientPin")) {
				patientPin = patientData.getInt("patientPin");
			}
			PatientSchedule patientSchedule = null;
			if(patientPin != -1)
				patientSchedule = reachService.createPatientSchedule(patientPin);

			if (patientSchedule == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(patientSchedule)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
			return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
		}catch (Exception e){
			System.out.println("Problem in HEAL Response builder");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
