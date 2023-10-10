package com.halfwind.resources;

import com.halfwind.api.Patient;
import com.halfwind.core.PatientService;
import org.glassfish.jersey.server.ManagedAsync;

import javax.net.ssl.SSLEngineResult;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.NotSupportedException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;


@Path("/patientservice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED})

public class PatientResource {

    PatientService patientService;

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    @GET
    @Path("/patients")
    public Response getPatients() {

        List<Patient> listOfPatients = patientService.getPatients();
        return Response.ok(listOfPatients).build();

    }

    @POST
    @Path("/patients")
    public Response createPatient(Patient patient) {

        String message = null;
        Long id = patient.getId();
        String name = patient.getName();

        try {
            patientService.createPatient(new Patient(id, name));
            message = "Resource created successfully";
        } catch (NotSupportedException e) {
            message = "Some error happened, please try later";
            return Response.status(500, message).build();
        }

        return Response.ok(message).build();

    }


    @POST
    @Path("/patients/v2")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED})
    public Response createPatientV2(@FormParam(value = "id") Long id, @FormParam(value = "name") String name, @Context HttpHeaders headers) {

        String message = null;

        try {
            patientService.createPatient(new Patient(id, name));
            message = "Resource created successfully";
        } catch (NotSupportedException e) {
            message = "Some error happened, please try later";
            return Response.status(500, message).build();
        }

        return Response.ok(message).build();
    }


    @GET
    @Path("/patient/{id}")
    public Response getPatient(@PathParam(value = "id") Long id) {

        Patient requestedPatient = patientService.getPatient(id);
        return Response.ok(requestedPatient).build();

    }

    @POST
    @Path("/patient/v3/")
    @ManagedAsync
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED})
    public void getPatientV3(@Suspended final AsyncResponse asyncResponse, @FormParam(value = "id") Long id, @FormParam(value = "name") String name, @Context HttpHeaders headers) {

        CompletableFuture<String> future = patientService.createPatientV2(new Patient(id, name));
        future.thenApply((result) -> asyncResponse.resume(Response.ok(result).build())).exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));
    }


}
