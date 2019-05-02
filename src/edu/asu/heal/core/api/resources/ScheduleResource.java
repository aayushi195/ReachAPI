package edu.asu.heal.core.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;
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

	/**
	 * @apiDefine BadRequestError
	 * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
	 * */

	/** @apiDefine ScheduleNotFoundError
	 * @apiError (Error 4xx) {404} NotFound Schedule cannot be found
	 * */

	/**
	 * @apiDefine InternalServerError
	 * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
	 * */

	/**
	 * @apiDefine NotImplementedError
	 * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
	 * */

	/**
	 * @api {get} /schedules?patientPin={patientPin} Get schedule for a specific Patient
	 * @apiName PatientScheduleDetail
	 * @apiGroup Schedules
	 * @apiParam {Number} id Patient's Unique Id
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/schedules?patientPin=4010
	 * @apiUse BadRequestError
	 * @apiUse ScheduleNotFoundError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@GET
	@Produces("application/hal+json")
	public Response getPatientSchedule(@QueryParam("patientPin") int patientPin) {
		HEALResponse response = null;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ScheduleResponse.class);

			if (patientPin == 0 || patientPin <= -1) {
				response = builder
						.setData("BAD VALUES FOR PARAMETER PATIENT PIN")
						.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			} else {
				PatientSchedule patientSchedule = reachService.getPatientSchedule(patientPin);
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
			}
			return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
		} catch (Exception e){
			System.out.println("Problem in HEAL Response builder");
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * @api {post} /schedules Create Schedule of a patient
	 * @apiName CreateSchedule
	 * @apiGroup Schedules
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/schedules
	 * @apiParamExample {json} Request-Payload:
	 * {
	 * 		"patientPin" : 4012
	 * }
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiuse ScheduleNotFoundError
	 * @apiUse NotImplementedError
	 */

	@POST
	@Consumes("application/json")
	@Produces("application/hal+json")
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
			if(patientPin > 0)
				patientSchedule = reachService.createPatientSchedule(patientPin);
			else if (patientPin == 0 || patientPin <= -1){
				response = builder
						.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
						.setData("BAD VALUES FOR PARAMETER PATIENT PIN")
						.build();
				return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
			}

			if (patientSchedule == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.CREATED.getStatusCode())
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

	/**
	 * @api {patch} /schedules Update schedule of the patient
	 * @apiName UpdateSchedule
	 * @apiGroup Schedules
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiParam {String} module Module Number
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/schedules
	 * @apiParamExample {json} Request-Payload:
	 * {
	 * 		"patientPin" : 4012
	 * 		"module" : "3"
	 * }
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiuse ScheduleNotFoundError
	 * @apiUse NotImplementedError
	 */

	@PATCH
	@Consumes("application/json")
	@Produces("application/hal+json")
	public Response updatePatientSchedule(String patientJson) {
		HEALResponse response = null;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ScheduleResponse.class);

			JSONObject patientData = new JSONObject(patientJson);
			int patientPin = -1;
			String module = null;
			if(patientData.has("patientPin")) {
				patientPin = patientData.getInt("patientPin");
			}
			if(patientData.has("module")) {
				module = patientData.getString("module");
			}
			PatientSchedule patientSchedule = null;
			if(patientPin > 0 && module != null && (Integer.parseInt(module) >= 1 && Integer.parseInt(module) <= 6))
				patientSchedule = reachService.updatePatientSchedule(patientPin, module);
			else if (patientPin == 0 || patientPin <= -1 || Integer.parseInt(module) < 1 || Integer.parseInt(module) > 6){
				response = builder
						.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
						.setData("BAD VALUES FOR PARAMETER PATIENT PIN AND MODULE")
						.build();
				return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
			}

			if (patientSchedule == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.CREATED.getStatusCode())
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
