package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.responses.PatientResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.reachv3.api.models.patientRewards.RewardsInstance;
import edu.asu.heal.reachv3.api.service.ReachService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/patients")
public class PatientResource {

	@Context
	private UriInfo _uri;

	private HealService reachService = HealServiceFactory.getTheService();


	/** @apiDefine PatientNotFoundError
	 * @apiError (Error 4xx) {404} NotFound Patient could not be found
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
	 * @api {get} /patients?trialId={trialId} Get list of all Patients
	 * @apiName GetPatients
	 * @apiGroup Patient
	 * @apiParam {Number} trialId Unique Id of a trial
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/patients?trialId=5a946ff566684905df608446
	 * @apiUse PatientNotFoundError
	 * @apiUse InternalServerError
	 */
	@GET
	@Produces("application/hal+json")
	public Response fetchPatients(@QueryParam("trialId") String trialId) {
		HEALResponse response = null;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(PatientResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			System.out.println("Problem in HEAL Response builder");
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		List<Patient> patients = reachService.getPatients(trialId);

		if (patients == null) {
			response = builder
					.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
					.build();
		} else if (patients.isEmpty()) {
			response = builder
					.setStatusCode(Response.Status.OK.getStatusCode())
					.setData("NO PATIENTS FOUND")
					.build();
		} else if (patients.size() == 1) {
			if (patients.get(0).equals(NullObjects.getNullPatient())) {
				response = builder
						.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
						.setData("THE TRIAL ID YOU'VE PASSED IN IS INCORRECT OR DOES NOT EXIST")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(patients)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		} else {
			response = builder
					.setStatusCode(Response.Status.OK.getStatusCode())
					.setData(patients)
					.setServerURI(_uri.getBaseUri().toString())
					.build();
		}

		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}

	/**
	 * @api {get} /patient/:patientPin Get detail for a specific Patient
	 * @apiName GetPatientDetail
	 * @apiGroup Patient
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiSampleRequest http://localhost:8080/ReachAPI/rest/patients/4014
	 * @apiUse InternalServerError
	 * @apiUse PatientNotFoundError
	 */
	@GET
	@Path("/{patientPin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchPatient(@PathParam("patientPin") int patientPin) {
		HEALResponse response = null;
		HEALResponseBuilder builder;
		try {
			builder = new HEALResponseBuilder(PatientResponse.class);
		}catch (IllegalAccessException | InstantiationException ie){
			System.out.println("Some server error");
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		Patient patient = reachService.getPatient(patientPin);
		if (patient == null) {
			response = builder
					.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
					.build();
		} else if (patient.equals(NullObjects.getNullPatient())) {
			response = builder
					.setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
					.setData("THE PATIENT YOU'RE REQUESTING DOES NOT EXIST")
					.build();
		} else {
			response = builder
					.setStatusCode(Response.Status.OK.getStatusCode())
					.setData(patient)
					.setServerURI(_uri.getBaseUri().toString())
					.build();
		}

		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}

	/**
	 * @api {post} /patient Create Patient
	 * @apiName AddPatient
	 * @apiGroup Patient
	 * @apiParam {String} Trial ID of the trial to which the patient needs to be added
	 * @apiParamExample {text} Request-Payload:
	 * 	  5a946ff566684905df608446
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/patients
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@POST
	public Response createPatient(String trialId) {
		HEALResponse response;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(PatientResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		Patient insertedPatient = reachService.createPatient(trialId);

		if (insertedPatient == null) {
			response = builder
					.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.setData("SOME ERROR CREATING NEW PATIENT. CONTACT ADMINISTRATOR")
					.build();
		} else if (insertedPatient.equals(NullObjects.getNullPatient())) {
			response = builder
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setData("INCORRECT TRIAL ID IN THE REQUEST")
					.build();
		} else {
			response = builder
					.setStatusCode(Response.Status.CREATED.getStatusCode())
					.setData(insertedPatient)
					.setServerURI(_uri.getBaseUri().toString())
					.build();
		}

		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}

	/**
	 * @api {put} /patients Update Patient
	 * @apiName updatePatients
	 * @apiGroup Patient
	 * @apiParam {json} Patient JSON structure
	 * @apiParamExample {json} Request-payload :
	 * {
	 * "pin": 4010,
	 * "startDate": "2018-10-23T07:00:00.000Z,
	 * "endDate": "2018-10-23T07:00:00.000Z,
	 * "state": "Active"
	 * }
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/patients
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePatients(Patient patient) {
		HEALResponse response;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(PatientResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		if (patient.getPin() == 0) {
			response = builder
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setData("YOU NEED TO PASS IN PATIENT PIN IN REQUEST PAYLOAD")
					.build();
		} else {

			Patient updatedPatient = reachService.updatePatient(patient);

			if (updatedPatient == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME ERROR UPDATING THE PATIENT. CONTACT ADMINISTRATOR")
						.build();
			} else if (updatedPatient.equals(NullObjects.getNullPatient())) {
				response = builder
						.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
						.setData("PATIENT PIN YOU PASSED IN IS INCORRECT OR DOES NOT EXIST")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.NO_CONTENT.getStatusCode())
						.setData(null)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		}

		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();

	}

	/**
	 * @api {get} /patient/:patientPin/rewards Get rewards for a specific Patient
	 * @apiName GetPatientRewards
	 * @apiGroup Patient
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiSampleRequest http://localhost:8080/ReachAPI/rest/patients/4014/rewards
	 * @apiUse InternalServerError
	 * @apiUse PatientNotFoundError
	 * @apiUse BadRequestError
	 * @apiUse PatientNotFoundError
	 */
	@GET
	@Path("/{patientPin}/rewards")
	public Response fetchPatientRewards(@PathParam("patientPin") int patientPin) {
		HEALResponse response = null;
		HEALResponseBuilder builder;
		RewardsInstance rewardsInstance = null;
		ReachService service =  (ReachService) reachService;
		try {
			builder = new HEALResponseBuilder(PatientResponse.class);
		}catch (IllegalAccessException | InstantiationException ie){
			System.out.println("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR");
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		if (patientPin == 0 || patientPin <= -1) {
			response = builder
					.setData("BAD VALUES FOR PARAMETER PATIENT PIN")
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setServerURI(_uri.getBaseUri().toString())
					.build();
			return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
		}else {
			rewardsInstance = service.getPatientRewards(patientPin);
			if (rewardsInstance == null) {
				 return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(rewardsInstance).build();

			} else {
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(rewardsInstance)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		}
		return Response.status(Response.Status.OK.getStatusCode()).entity(rewardsInstance).build();
	}
}
