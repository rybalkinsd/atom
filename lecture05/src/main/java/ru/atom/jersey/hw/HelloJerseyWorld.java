package ru.atom.jersey.hw;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloJerseyWorld {

    @GET
    @Produces("text/plain")
    public Response sayHello() {
        return Response.ok("Hello jersey world")
                .build();
    }
}
