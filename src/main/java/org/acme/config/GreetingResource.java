package org.acme.config;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/")
public class GreetingResource {

    @Inject
    AppConfig config;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("Accessing application configuration");
        String result = config.stringify();
        System.out.println("Configuration: " + result);
        return "Hello from Quarkus! \n" + result;
    }
}
