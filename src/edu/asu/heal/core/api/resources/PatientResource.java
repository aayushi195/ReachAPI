package edu.asu.heal.core.api.resources;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.responses.HEALResponseBuilder;
import edu.asu.heal.core.api.responses.PatientResponse;
import edu.asu.heal.core.api.service.IHealService;
import edu.asu.heal.core.api.service.ReachServiceFactory;

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

    private IHealService reachService =
            ReachServiceFactory.getTheService();


    /** @apiDefine PatientNotFoundError
     * @apiError (Error 4xx) {404} NotFound The patient cannot be found
     * */

    /**
     * @apiDefine PatientsNotFoundError
     * @apiError (Error 4xx) {404} NotFound No patients exist!
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
     * @api {get} /patient Get Patients
     * @apiName GetPatients
     * @apiGroup Patient
     * @apiParam {Number} [trialId] Pass trialId = 'some-unique-id' as query parameter to fetch the list of
     * patients for a particular trial; eg: `/patient?trialId=1`
     * @apiSuccess {Object[]} data List of Patients
     * @apiSuccess {Object[]} data.activityInstances Patient's Activity Instances
     * @apiSuccess {DateTime} data.createdAt CreatedAt
     * @apiSuccess {Number} data.pin Patient's pin
     * @apiSuccess {DateTime} data.startDate Patient's Start Date of Trial
     * @apiSuccess {String} data.state Patient's Current State in the Trial
     * @apiSuccess {DateTime} data.updatedAt UpdatedAt
     * @apiSuccess {String} message Response Message
     * @apiSuccess {String} messageType Response Message Type
     * @apiSuccess {Number} statusCode  Response Status Code
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": [
     * {
     * "activityInstances": [
     * {
     * id: 87612kjbacskhv121
     * }
     * ],
     * "createdAt": "2018-02-26T07:00:00Z[UTC]",
     * "pin": 4010,
     * "startDate": "2018-02-26T07:00:00Z[UTC]",
     * "state": "Active",
     * "updatedAt": "2018-02-26T07:00:00Z[UTC]"
     * }
     * ],
     * "message": "Success",
     * "messageType": "success",
     * "statusCode": 200
     * }
     * @apiUse PatientsNotFoundError
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
     * @api {get} /patient/:id Patient Detail
     * @apiName GetPatientDetail
     * @apiGroup Patient
     * @apiParam {Number} id Patient's Unique Id
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
     * @api {post} /patient Add Patient
     * @apiName AddPatient
     * @apiGroup Patient
     * @apiParam {String} Trial ID of the trial to which the patient needs to be added
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
     * "startDate": "2018-01-01 13:00:00",
     * "endDate": "2018-03-01 13:00:00",
     * "state": "completed"
     * }
     * @apiSuccess {int} requestCode Status code indicating NO_CONTENT
     * @apiSuccessExample {int} Success- Example : 204
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

}
