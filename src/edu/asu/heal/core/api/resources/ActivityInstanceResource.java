package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.ActivityInstanceResponse;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/activityinstances/")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityInstanceResource {

	private static HealService reachService = HealServiceFactory.getTheService();
	@Context
	private UriInfo _uri;

	/**
	 * @apiDefine BadRequestError
	 * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
	 * */

	/** @apiDefine ActivityInstanceNotFoundError
	 * @apiError (Error 4xx) {404} NotFound ActivityInstance(s) cannot be found
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
	 * @api {get} /activityinstances?patientPin={patientPin} Get Activity Instances for a patient
	 * @apiName GetActivityInstancesOfPatient
	 * @apiGroup ActivityInstance
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activityinstances?patientPin=4010
	 * @apiUse BadRequestError
	 * @apiUse ActivityInstanceNotFoundError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@GET
	public Response fetchActivityInstances(@QueryParam("patientPin") int patientPin) {
		HEALResponse response;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ActivityInstanceResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
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
			List<ActivityInstance> instances = reachService.getActivityInstances(patientPin);
			if (instances == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData(NullObjects.getNullActivityInstance())
						.build();
			} else if (instances.isEmpty()) {
				instances.add(NullObjects.getNullActivityInstance());
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(instances)
						.build();
			} else if (instances.size() == 1) {
				if (instances.get(0).equals(NullObjects.getNullActivityInstance())) {
					response = builder
							.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
							.setData("THE PATIENT PIN YOU'VE PASSED IN IS INCORRECT OR DOES NOT EXIST")
							.build();
				} else {
					response = builder
							.setStatusCode(Response.Status.OK.getStatusCode())
							.setData(instances)
							.setServerURI(_uri.getBaseUri().toString())
							.build();
				}
			} else {
				response = builder
						.setStatusCode(Response.Status.OK.getStatusCode())
						.setData(instances)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		}
		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}

	/**
	 * @api {get} /activityinstances/:activityInstanceId Get Activity Instances for an activity Instance Id
	 * @apiName ActivityInstanceDetail
	 * @apiGroup ActivityInstance
	 * @apiParam {String} activityInstanceId ActivityInstance's Unique Id
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activityinstances/5c5b901a324b051370ac2f3e
	 * @apiUse BadRequestError
	 * @apiUse ActivityInstanceNotFoundError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@GET
	@Path("/{id}")
	public Response fetchActivityInstance(@PathParam("id") String activityInstanceId) {
		HEALResponse response;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ActivityInstanceResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		ActivityInstance instance = reachService.getActivityInstance(activityInstanceId);

		if (instance == null) {
			response = builder
					.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
					.build();
		} else if (instance.equals(NullObjects.getNullActivityInstance())) {
			response = builder
					.setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
					.setData("THE ACTIVITY INSTANCE YOU'RE REQUESTING DOES NOT EXIST")
					.build();
		} else {
			response = builder
					.setStatusCode(Response.Status.OK.getStatusCode())
					.setData(instance)
					.setServerURI(_uri.getBaseUri().toString())
					.build();
		}

		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}

	/**
	 * @api {post} /activityinstances Create an ActivityInstance
	 * @apiName CreateActivityInstance
	 * @apiGroup ActivityInstance
	 * @apiParam {String} ActivityId Unique id of the activity
	 * @apiParam {String} Description Description about the Activity Instance
	 * @apiParam {DateTime} StartTime Start Time of the Activity Instance
	 * @apiParam {DateTime} EndTime End Time of the Activity Instance
	 * @apiParam {DateTime} UserSubmissionTime User Submission Time of the ActivityInstance
	 * @apiParam {DateTime} ActualSubmissionTime Actual Submission Time of the ActivityInstance
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiParamExample {json} Request-Payload:
	 * {
	 * 	"activityId": "5a9496ef66684905df624348",
	 * 	"description": "SWAP activity",
	 * 	"startTime": "2018-10-23T07:00:00.000Z",
	 * 	"endTime": null,
	 * 	"userSubmissionTime": null,
	 * 	"actualSubmissionTime": null,
	 * 	"patientPin": 4011
	 * }
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activityinstances
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createActivityInstance(ActivityInstance activityInstanceJson) {
		HEALResponse response;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ActivityInstanceResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		if (activityInstanceJson.getPatientPin() == 0) {
			response = builder
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setData("REQUEST MUST CONTAIN AT LEAST PATIENT PIN AND INSTANCE TYPE VALUE")
					.build();

		} else {
			ActivityInstance instance = reachService.createActivityInstance(activityInstanceJson);
			if (instance == null) {
				response = builder
						.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
						.setData("SOME ERROR CREATING NEW ACTIVITY INSTANCE. CONTACT ADMINISTRATOR")
						.build();
			} else if (instance.equals(NullObjects.getNullActivityInstance())) {
				response = builder
						.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
						.setData("INCORRECT PATIENT PIN IN THE REQUEST PAYLOAD")
						.build();
			} else {
				response = builder
						.setStatusCode(Response.Status.CREATED.getStatusCode())
						.setData(instance)
						.setServerURI(_uri.getBaseUri().toString())
						.build();
			}
		}

		return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
	}

	/**
	 * @api {put} /activityinstances/:activityInstanceId Update an ActivityInstance
	 * @apiName UpdateActivityInstance
	 * @apiGroup ActivityInstance
	 * @apiParam {String} ActivityInstanceId ActivityInstance's Unique Id
	 * @apiParam {String} ActivityId Unique id of the activity
	 * @apiParam {String} Description Description about the Activity Instance
	 * @apiParam {DateTime} createdAt Created Date and Time of the Activity Instance
	 * @apiParam {DateTime} updatedAt Updated Data and Time of the Activity Instance
	 * @apiParam {DateTime} StartTime Start Time of the Activity Instance
	 * @apiParam {DateTime} EndTime End Time of the Activity Instance
	 * @apiParam {DateTime} UserSubmissionTime User Submission Time of the ActivityInstance
	 * @apiParam {DateTime} ActualSubmissionTime Actual Submission Time of the ActivityInstance
	 * @apiParam {Number} patientPin Patient's Unique Id
	 * @apiParam {String} state The status of the Activity Instance from Created | In Progress (Running) | Completed
	 * @apiParamExample {json} Request-Payload:
	 * 	{
	 * 		"activityInstanceId": "5cca73cf89f38e3d343fdcae",
	 * 		"activityId": "5a9496ef66684905df624348",
	 * 		"createdAt": 1556771790775,
	 * 		"updatedAt": 1556771790776,
	 * 		"description": "SWAP activity",
	 * 		"startTime": 1540278000000,
	 * 		"endTime": null,
	 * 		"userSubmissionTime": null,
	 * 		"actualSubmissionTime": null,
	 * 		"state": "created",
	 * 		"patientPin": 4012,
	 * 		"extended": {
	 * 			"domainName": "Preventive Anxiety",
	 * 			"activityTypeName": "SWAP",
	 * 			"version": "v1",
	 * 			"situation": {
	 * 				"situationId": 1,
	 * 				"situationTitle": "SWAP Situation",
	 * 				"questions": [
	 * 					{
	 * 						"questionId": 1,
	 * 						"questionText": "SITUATION (NOTICE THE SITUATION THAT MAKES YOU WORRY, SCARED OR NERVOUS)",
	 * 						"answerText": null
	 * 					},
	 * 					{
	 * 						"questionId": 2,
	 * 						"questionText": "WORRY (NOTICE THE WORRY THAT POPS INTO YOUR HEAD)",
	 * 						"answerText": null
	 * 					},
	 * 					{
	 * 						"questionId": 3,
	 * 						"questionText": "ACTION THOUGHT OR PLAN (WHAT ELSE CAN HAPPEN? HOW CAN YOU SOLVE THAT?)",
	 * 						"answerText": null
	 * 					},
	 * 					{
	 * 						"questionId": 4,
	 * 						"questionText": "PRACTICE (ACTION THOUGHT OR PLAN)",
	 * 						"answerText": null
	 * 					}
	 * 				]
	 * 			}
	 * 		}
	 * 	}
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activityinstances/5c5b901a324b051370ac2f3e
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@PUT
	@Path("/{activityInstanceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateActivityInstance(@PathParam("activityInstanceId") String activityInstanceId, String payload) {
		ActivityInstance instance = reachService.updateActivityInstance(payload);
		HEALResponse response;
		HEALResponseBuilder builder;
		try{
			builder = new HEALResponseBuilder(ActivityInstanceResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		if(instance == null){
			response = builder
					.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.setData("Some error on the server side")
					.build();
			return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
		}else if(instance == NullObjects.getNullActivityInstance()){
			response = builder
					.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
					.setData("Incorrect request format or activity instance not found")
					.build();
			return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
		}else{
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	/**
	 * @api {delete} /activityinstances/:activityInstanceId Delete an ActivityInstance
	 * @apiName DeleteActivityInstance
	 * @apiGroup ActivityInstance
	 * @apiParam {String} activityInstanceId ActivityInstance's unique id
	 * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activityinstances/5c5b901a324b051370ac2f3e
	 * @apiUse BadRequestError
	 * @apiUse ActivityInstanceNotFoundError
	 * @apiUse InternalServerError
	 * @apiUse NotImplementedError
	 */
	@DELETE
	@Path("/{id}")
	public Response removeActivityInstance(@PathParam("id") String activityInstanceId) {
		HEALResponse response;
		HEALResponseBuilder builder;

		try{
			builder = new HEALResponseBuilder(ActivityInstanceResponse.class);
		}catch (InstantiationException | IllegalAccessException ie){
			ie.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		ActivityInstance removed = reachService.deleteActivityInstance(activityInstanceId);

		if (removed.equals(NullObjects.getNullActivityInstance())) {
			response = builder
					.setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
					.setData("ACTIVITY INSTANCE DOES NOT EXIST")
					.build();
		} else if (removed == null) {
			response = builder
					.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
					.setData("SOME PROBLEM IN DELETING ACTIVITY INSTANCE. CONTACT ADMINISTRATOR")
					.build();
		} else {
			response = builder
					.setStatusCode(Response.Status.NO_CONTENT.getStatusCode())
					.setData(null)
					.build();
		}
		return Response.status(response.getStatusCode()).build();

	}
}

