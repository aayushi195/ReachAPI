package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.responses.TrialResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/trials")
@Produces(MediaType.APPLICATION_JSON)
public class TrialsResource {
    @Context
    private UriInfo _uri;

    private static HealService reachService = HealServiceFactory.getTheService();

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */

    /** @apiDefine TrialNotFoundError
     * @apiError (Error 4xx) {404} NotFound Trial cannot be found
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
     * @api {get} /trials?domain={domainName} Get list of trials for a given domain
     * @apiName getTrials
     * @apiGroup Trials
     * @apiParam {String} domain Domain name for which trials are to be fetched.
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/trials?domain=Preventive Anxiety
     * @apiUse BadRequestError
     * @apiUse TrialNotFoundError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @QueryParam("domain")
    public Response getTrials(@QueryParam("domain") String domain) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try{
            builder = new HEALResponseBuilder(TrialResponse.class);
        }catch (InstantiationException | IllegalAccessException ie){
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        List<Trial> trials;
        if (domain == null || domain.equals("")) {
            trials = reachService.getTrials(null);
        } else {
            trials = reachService.getTrials(domain);
        }

        if (trials == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        } else if (trials.isEmpty()) {
            response = builder
                    .setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
                    .setData("THERE ARE NO TRIALS IN THE DATABASE")
                    .build();
        } else if (trials.size() == 1) {
            if (trials.get(0).equals(NullObjects.getNullActivityInstance())) {
                response = builder
                        .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .setData("THE DOMAIN YOU'VE PASSED IN IS INCORRECT OR DOES NOT EXIST")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.OK.getStatusCode())
                        .setData(trials)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
            }
        } else {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(trials)
                    .setServerURI(_uri.getBaseUri().toString())
                    .build();
        }

        return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
    }

    /**
     * @api {post} /trials Create Trial
     * @apiName CreateTrial
     * @apiGroup Trials
     * @apiParam {String} domainId DomainId for which the trial is being created
     * @apiParam {String} title Title of the Trial
     * @apiParam {String} description Description of the Trial
     * @apiParam {String} startDate Start Date for the Trial
     * @apiParam {String} endDate End Date for the Trial
     * @apiParam {Number} targetCount Target Count of the Trial
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/trials
     * @apiParamExample {json} Request-Payload:
         * {
         *   "domainId":"5abd64f5734d1d0cf303bda1",
         *   "title":"SCD",
         *   "description":"Sickle Cell Disease",
         *   "startDate":"2018-10-23T07:00:00.000Z",
         *   "endDate":"2019-10-23T07:00:00.000Z",
         *   "targetCount":100
         * }
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiuse TrialNotFoundError
     * @apiUse NotImplementedError
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrial(Trial trial) {

        HEALResponse response;
        HEALResponseBuilder builder;
        try{
            builder = new HEALResponseBuilder(TrialResponse.class);
        }catch (InstantiationException | IllegalAccessException ie){
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (trial.getDomainId().length() <= 0) {
            response = builder
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setData("DOMAIN CANNOT BE EMPTY")
                    .build();
        } else if (trial.getTitle().length() <= 0) {
            response = builder
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setData("TITLE CANNOT BE EMPTY")
                    .build();
        } else {
            Trial addedTrial = reachService.addTrial(trial);
            if (addedTrial.equals(NullObjects.getNullTrial())) {
                response = builder
                        .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .setData("DOMAIN IS INCORRECT OR DOES NOT EXIST")
                        .build();
            } else if (addedTrial == null) {
                response = builder
                        .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .setData("SOME ERROR CREATING NEW TRIAL. CONTACT ADMINISTRATOR")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.CREATED.getStatusCode())
                        .setData(addedTrial)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
            }
        }
        return Response.status(response.getStatusCode()).entity(response).build();
    }
}
