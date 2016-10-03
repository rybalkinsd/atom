package ru.atom.server.api;

import ru.atom.server.api.PersonBatchHolder;
import ru.atom.model.Person;
import ru.atom.server.auth.Authorized;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/data")
public class DataProvider {

    @Authorized
    @POST
    @Path("dummy")
    public Response dummy() {
        return Response.ok().build();
    }

    // curl -X POST -H 'Authorization: Bearer {token}' http://localhost:8080/data/personsbatch
    @Authorized
    @POST
    @Path("personsbatch")
    public Response getPersonsBatch() throws IOException {
        Person person = Person.readJson(FRY_JSON);
        return Response.ok(
                PersonBatchHolder.of(person).writeJson()
            ).build();
    }

    private static final String FRY_JSON =
            "{" +
                    "\"id\":\"44d91146-a6b7-45fa-888d-bad4ad0301e0\"," +
                    "\"gender\":\"MALE\"," +
                    "\"name\":\"Stephen\"," +
                    "\"age\":59," +
                    "\"location\":{" +
                    "\"latitude\":51.5287718," +
                    "\"longitude\":-0.2416814" +
                    "}," +
                    "\"desctiption\":\"Actor and writer currently working on new TV comedy.\"," +
                    "\"image\":{" +
                    "\"url\":\"http://hitgid.com/images/%D1%81%D1%82%D0%B8%D0%B2%D0%B5%D0%BD-%D1%84%D1%80%D0%B0%D0%B9-2.jpg\"," +
                    "\"width\":360," +
                    "\"height\":288}," +
                    "\"instagramUrl\":\"https://www.instagram.com/stephenfryactually/\"" +
                    "}";
}
