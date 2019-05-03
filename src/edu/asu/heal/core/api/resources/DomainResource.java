package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.DomainResponse;
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

@Path("/domains")
@Produces(MediaType.APPLICATION_JSON)
public class DomainResource {

    @Context
    private UriInfo _uri;

    private static HealService reachService =
            HealServiceFactory.getTheService();

    /**
     * @apiDefine DomainNotFoundError
     * @apiError (Error 4xx) {404} DomainNotFoundError Domain Not Found!
     * */

    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

    /**
     * @api {get} /domains Get list of all Domains
     * @apiName GetDomains
     * @apiGroup Domain
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/domains
     * @apiUse DomainNotFoundError
     * @apiUse InternalServerError
     */
    @GET
    @Produces("application/hal+json")
    public Response fetchDomains() {

        HEALResponse response;
        HEALResponseBuilder builder;
        try{
            builder = new HEALResponseBuilder(DomainResponse.class);
        }catch (InstantiationException | IllegalAccessException ie){
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        List<Domain> domains = reachService.getDomains();
        if (domains == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        } else if (domains.isEmpty()) {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData("THERE ARE NO DOMAINS IN THE DATABASE")
                    .build();
        } else {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(domains)
                    .setServerURI(_uri.getBaseUri().toString())
                    .build();
        }

        return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
    }

    /**
     * @api {get} /domains/:id Get a specific domain
     * @apiName DomainDetail
     * @apiGroup Domain
     * @apiParam {String} id Domain's Unique Id
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/domains/5abd64f5734d1d0cf303bda1
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiUse NotImplementedError
     */
    @GET
    @Path("/{id}")
    @Produces("application/hal+json")
    public Response fetchDomain(@PathParam("id") String id) {

        HEALResponse response;
        HEALResponseBuilder builder;
        try{
            builder = new HEALResponseBuilder(DomainResponse.class);
        }catch (InstantiationException | IllegalAccessException ie){
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        Domain domain = reachService.getDomain(id);
        if (domain == null) {
            response = builder
                    .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .setData("SOME SERVER ERROR. PLEASE CONTACT ADMINISTRATOR")
                    .build();
        } else if (domain.equals(NullObjects.getNullDomain())) {
            response = builder
                    .setStatusCode(Response.Status.NOT_FOUND.getStatusCode())
                    .setData("THE DOMAIN YOU'RE REQUESTING DOES NOT EXIST")
                    .build();
        } else {
            response = builder
                    .setStatusCode(Response.Status.OK.getStatusCode())
                    .setData(domain)
                    .setServerURI(_uri.getBaseUri().toString())
                    .build();
        }

        return Response.status(response.getStatusCode()).entity(response.toEntity()).build();
    }


    /**
     * @api {post} /domains Create Domain
     * @apiName CreateDomain
     * @apiGroup Domain
     * @apiSampleRequest http://localhost:8080/CompassAPI/rest/domains
     * @apiParamExample {json} Request-Payload:
        * {
            * "title": "Preventive Anxiety",
            * "description": "Heal domain for preventive anxiety applicatons",
            * "state": "Active"
        * }
     * @apiUse DomainNotFoundError
     * @apiUse BadRequestError
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/hal+json")
    public Response addDomain(Domain domain) {
        HEALResponse response;
        HEALResponseBuilder builder;
        try{
            builder = new HEALResponseBuilder(DomainResponse.class);
        }catch (InstantiationException | IllegalAccessException ie){
            ie.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (domain.getTitle().length() == 0) {
            response = builder
                    .setStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                    .setData("TITLE SHOULD NOT BE MISSING FROM THE REQUEST")
                    .build();

        } else {
            Domain createdDomain = reachService.addDomain(domain.getTitle(), domain.getDescription(), domain.getState());
            if (createdDomain == null) {
                response = builder
                        .setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .setData("SOME ERROR CREATING NEW DOMAIN. CONTACT ADMINISTRATOR")
                        .build();
            } else {
                response = builder
                        .setStatusCode(Response.Status.CREATED.getStatusCode())
                        .setData(createdDomain)
                        .setServerURI(_uri.getBaseUri().toString())
                        .build();
            }
        }

        return Response.status(response.getStatusCode()).entity(response).build();


    }

}
