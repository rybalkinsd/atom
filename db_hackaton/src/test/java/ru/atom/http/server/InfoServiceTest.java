package ru.atom.http.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by zarina on 26.03.17.
 */
public class InfoServiceTest extends JerseyTest {
    private String pathUsers = "data/users";
    private String pathOnline = "data/online";

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(InfoService.class);
    }

    @Test
    public void users() throws Exception {
        Response output = target(pathUsers).request().get();
        assertEquals(output.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(output.getMediaType().toString(), MediaType.APPLICATION_JSON);
    }

    @Test
    public void online() throws Exception {
        Response output = target(pathOnline).request().get();
        assertEquals(output.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(output.getMediaType().toString(), MediaType.APPLICATION_JSON);
    }

}
