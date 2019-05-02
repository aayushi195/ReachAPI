package edu.asu.heal.core.api.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.ActivityResponse;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;
import edu.asu.heal.reachv3.api.service.ReachService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.List;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {

    @Context
    private UriInfo _uri;

    private static HealService reachService =
            HealServiceFactory.getTheService();

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine ActivityNotFoundError
     * @apiError (Error 4xx) {404} NotFound Activity cannot be found
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
     * @api {get} /activities?domain={domainName} Get list of Activities for a given domain
     * @apiName GetActivitiesByDomain
     * @apiGroup Activity
     * @apiParam {String} domain Domain name for which activities are to be fetched.
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activities?domain=Preventive Anxiety
     * @apiUse ActivityNotFoundError
     * @apiUse InternalServerError
     * @apiUse BadRequestError
     */
    @GET
    @QueryParam("domain")
    public Response getActivities(@QueryParam("domain") String domain) {
        List<Activity> activities = reachService.getActivities(domain);

        HEALResponse response;
        HEALResponseBuilder builder;
        try {
            builder = new HEALResponseBuilder(ActivityResponse.class);
        } catch (InstantiationException | IllegalAccessException ie) {
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (activities == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        } else if (activities.isEmpty()) {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData("THERE ARE NO ACTIVITIES FOR THIS DOMAIN")
                    .build();
        } else if (activities.size() == 1) {
            if (activities.get(0).equals(NullObjects.getNullActivity())) {
                response = builder
                        .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .setData("THE DOMAIN YOU'VE PASSED IN IS INCORRECT OR DOES NOT EXIST")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.OK.getStatusCode())
                        .setData(activities)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
            }
        } else {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(activities)
                    .setServerURI(_uri.getBaseUri().toString())
                    .build();
        }
        return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
    }

    /**
     * @api {get} /activities/:activityId Get an Activity for an activity Id
     * @apiName ActivityDetail
     * @apiGroup Activity
     * @apiParam {String} activityId Activity's Unique Id
     * @apiSampleRequest http://localhost:8080/ReachAPI/rest/activities/5a9496ef66684905df624348
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @Path("/{activityId}")
    public Response getActivity(@PathParam("activityId") String activityId) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try {
            builder = new HEALResponseBuilder(ActivityResponse.class);
        } catch (InstantiationException | IllegalAccessException ie) {
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        Activity activity = reachService.getActivity(activityId);

        if (activity == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        } else if (activity.equals(NullObjects.getNullActivity())) {
            response = builder
                    .setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
                    .setData("THE ACTIVITY YOU'RE REQUESTING DOES NOT EXIST")
                    .build();
        } else {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(activity)
                    .setServerURI(_uri.getBaseUri().toString())
                    .build();
        }
        return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
    }

    /**
     * @api {get} /activities/suggestions?patientPin={patientPin}&emotion={emotionName}&intensity={intensityValue} Get suggested activities based on the emotion name and intensity level
     * @apiName GetSuggestedActivities
     * @apiGroup Activity
     * @apiParam {Number} patientPin Patient's Unique Id
     * @apiParam {String} emotion Name of the emotion
     * @apiParam {Number} intensity Intensity level of the emotion
     * @apiSampleRequest http://localhost:8080/ReachAPI/rest/activities/suggestions?patientPin=4015&emotion=sad&intensity=5
     * @apiUse BadRequestError
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @Path("/suggestions")
    public Response getSuggestedActivities(@QueryParam("patientPin") int patientPin,
                                           @QueryParam("emotion") String emotion,
                                           @QueryParam("intensity") int intensity) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try {
            builder = new HEALResponseBuilder(ActivityResponse.class);
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
            // Task #396 - Activity suggestion according to emotion and its intensity.
            if (emotion != null) {
                ReachService service = (ReachService) reachService;
                List<Activity> emotionsActivityResponse = service.getEmotionsActivityInstance(patientPin, emotion, intensity);
                if (emotionsActivityResponse == null) {
                    response = builder
                            .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                            .build();
                } else if (emotionsActivityResponse.isEmpty()) {
                    response = builder
                            .setStatusCode(Response.Status.OK.getStatusCode())
                            .setData("THERE ARE NO ACTIVITIES SUGESSTION FOR THIS EMOTION AND INTENSITY")
                            .build();
                } else {
                    response = builder
                            .setStatusCode(Response.Status.OK.getStatusCode())
                            .setData(emotionsActivityResponse)
                            .setServerURI(_uri.getBaseUri().toString())
                            .build();
                }
            } else {
                // Task #386 : Has to be done more general to satisfy activities suggestion for any activity.
                response = builder
                        .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .setData("EMOTION PARAMETER IS MISSING")
                        .build();
            }
        }
        return Response.status(response.getStatusCode()).entity(response.toEntity()).build();

    }

    /**
     * @api {post} /activities Create an Activity
     * @apiName CreateActivity
     * @apiGroup Activity
     * @apiParam {String} Title Title of the Activity
     * @apiParam {String} Description Description of the Activity
     * @apiParamExample {json} Activity Example:
     *      {
     *          "title" : "SWAP",
     *          "description" : "SWAP Activity"
     *      }
     * @apiSampleRequest http://localhost:8080/ReachAPI/rest/activities
     * @apiUse InternalServerError
     * @apiUse BadRequestError
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createActivity(Activity activityJSON) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try {
            builder = new HEALResponseBuilder(ActivityResponse.class);
        } catch (InstantiationException | IllegalAccessException ie) {
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (activityJSON.getTitle() == null || activityJSON.getTitle().trim().length() == 0) {
            response = builder
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setData("TITLE MISSING FROM REQUEST")
                    .build();
        } else {
            Activity activity = reachService.createActivity(activityJSON.getTitle(), activityJSON.getDescription());
            if (activity == null) {
                response = builder
                        .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .setData("COULD NOT CREATE ACTIVITY. CONTACT ADMINISTRATOR")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.CREATED.getStatusCode())
                        .setData(activity)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
            }
        }
        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {put} /activities Update an Activity
     * @apiName UpdateActivity
     * @apiGroup Activity
     * @apiParam {String} Title Title of the Activity
     * @apiParam {String} Description Description of the Activity
     * @apiParam {String} ActivityId Unique Id of an Activity
     * @apiParam {DateTime} createdAt Created Date and Time of the Activity
     * @apiParam {DateTime} updatedAt Updated Data and Time of the Activity
     * @apiParamExample {json} Activity Example:
     * {
     *         "activityId": "5a9496ef66684905df624348",
     *         "createdAt": "2019-05-02T05:29:33.207Z[UTC]",
     *         "description": "SWAP Activity",
     *         "title": "SWAP",
     *         "updatedAt": "2019-05-02T05:29:33.207Z[UTC]"
     * }
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activities
     * @apiUse InternalServerError
     * @apiUse BadRequestError
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateActivity(Activity activityJSON) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try {
            builder = new HEALResponseBuilder(ActivityResponse.class);
        } catch (InstantiationException | IllegalAccessException ie) {
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (activityJSON.getActivityId() == null || activityJSON.getActivityId().length() == 0) {
            response = builder
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setData("ACTIVITYID MISSING FROM REQUEST")
                    .build();
        } else {
            Activity updatedActivity = reachService.updateActivity(activityJSON);
            if (updatedActivity == null) {
                response = builder
                        .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .setData("COULD NOT UPDATE ACTIVITY. CONTACT ADMINISTRATOR")
                        .build();
            } else if (updatedActivity.equals(NullObjects.getNullActivity())) {
                response = builder
                        .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .setData("ACTIVITY ID YOU PASSED IN IS INCORRECT OR DOES NOT EXIST")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.OK.getStatusCode())
                        .setData(updatedActivity)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
            }
        }
        return Response.status(response.getStatusCode()).entity(response).build();
    }

    /**
     * @api {delete} /activities/:activityId Delete an Activity
     * @apiName DeleteActivity
     * @apiGroup Activity
     * @apiParam {String} activityId Activity's Unique Id
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/activities/5a9496ef66684905df624348
     * @apiUse ActivityInstanceNotFoundError
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */

    @DELETE
    @Path("/{id}")
    public Response removeActivity(@PathParam("id") String activityId) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try {
            builder = new HEALResponseBuilder(ActivityResponse.class);
        } catch (InstantiationException | IllegalAccessException ie) {
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        Activity removedActivity = reachService.deleteActivity(activityId);

        if (removedActivity.equals(NullObjects.getNullActivity())) {
            response = builder
                    .setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
                    .setData("ACTIVITY DOES NOT EXIST")
                    .build();
        } else if (removedActivity == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME PROBLEM IN DELETING ACTIVITY. CONTACT ADMINISTRATOR")
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
