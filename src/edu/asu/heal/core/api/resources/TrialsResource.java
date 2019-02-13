package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.responses.TrialResponse;
import edu.asu.heal.core.api.service.IHealService;
import edu.asu.heal.core.api.service.ReachServiceFactory;

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

//    private static IHealService reachService =
//            ReachServiceFactory.getTheService();

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
     * @apiParam {String} domain Domain name for which trials are to be fetched. Use "_" in place of space character. Case sensitive.
     * @apiParamExample Request example: http://localhost:8080/ReachAPI/rest/trials?domain=Anxiety_Prevention
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @QueryParam("domain")
    public Response getTrials(@QueryParam("domain") String domain) {
		IHealService reachService = ReachServiceFactory.getFactory().getTheService();
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
                    .setStatusCode(Response.Status.OK.getStatusCode())
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
     * @apiGroup Trial
     * @apiParam {String} domainId DomainId for which the trial is being created
     * @apiParam {String} title Title of the Trial
     * @apiParam {String} description Description of the Trial
     * @apiParam {String} startDate Start Date for the Trial
     * @apiParam {String} endDate End Date for the Trial
     * @apiParam {Number} targetCount Target Count of the Trial
     * @apiParamExample {form-data} Request Example:
     * domainId="Preventive Anxiety
     * title="Compass"
     * description="Compass trial for Preventive Anxiety domain
     * startDate="2018-02-18 09:00:00"
     * endDate="2018-03-17 09:00:00"
     * targetCount=100
     * @apiSuccess {String} text SUCCESS
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiuse TrialNotFoundError
     * @apiUse NotImplementedError
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrial(Trial trial) {
		IHealService reachService = ReachServiceFactory.getFactory().getTheService();
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
