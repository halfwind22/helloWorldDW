package com.halfwind.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Path("/helloworld")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    private final String template;
    private final String defaultName;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(@QueryParam("name") Optional<String> name, @Context Response response) throws IOException, InterruptedException {
        String message = String.format(template, name.orElse(defaultName));
        return Response.fromResponse(response).build();


    }
}