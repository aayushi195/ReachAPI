package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.ActivityResponse;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.HealServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/logger")
@Produces(MediaType.APPLICATION_JSON)
public class LoggerResource {
    @Context
    private UriInfo _uri;

    private static HealService reachService = HealServiceFactory.getTheService();

    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */

    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

    /**
     * @api {post} /logger Create Logs
     * @apiName AddLogs
     * @apiGroup Logger
     * @apiParam {array} loggerJSON array of logs in JSON format
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/logger
     * @apiParamExample {array} Request-Payload:
        * [
            * {
                * "format" : "JSON",
                * "level" : "INFO",
                * "metadata" : "{\"buttonName\":\"pressBtn\",\"methodName\":\"onClick\"}",
                * "subtype" : "Settings",
                * "timeStamp" : "19.01.2019 14:46:50",
                * "trialId" : "5a946ff566684905df608446",
                * "type" : "UI_EVENT"
            * }
        * ]
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response storeLogs(Logger[] loggerJSON) {

        HEALResponse response;
        HEALResponseBuilder builder;
        try{
            builder = new HEALResponseBuilder(ActivityResponse.class);
        }catch (InstantiationException | IllegalAccessException ie){
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        // return type will be changed
        Logger[] logger = reachService.logMessage(loggerJSON);
        if (logger == null) {
            response = builder
                        .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .setData("COULD NOT STORE LOG. CONTACT ADMINISTRATOR")
                        .build();
        } else {
            response = builder
                        .setStatusCode(Response.Status.CREATED.getStatusCode())
                        .setData(logger)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
        }
        return Response.status(response.getStatusCode()).entity(response).build();
    }
}