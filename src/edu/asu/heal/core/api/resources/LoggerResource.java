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
import java.util.List;

@Path("/logger")
@Produces(MediaType.APPLICATION_JSON)
public class LoggerResource {
    @Context
    private UriInfo _uri;

    private static HealService reachService =
            HealServiceFactory.getTheService();

    /**
     * @api {post} /logger Add Logs
     * @apiName AddLogs
     * @apiGroup Logger
     * @apiParam {Array} loggerJSON array of logs in JSON format
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