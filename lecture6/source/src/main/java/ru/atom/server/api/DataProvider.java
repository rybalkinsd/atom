package ru.atom.server.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.dao.PersonDao;
import ru.atom.model.data.Gender;
import ru.atom.model.data.Image;
import ru.atom.model.data.Location;
import ru.atom.model.data.person.Person;
import ru.atom.model.data.person.PersonBatchHolder;
import ru.atom.server.auth.Authorized;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Path("/data")
public class DataProvider {
    private static final Logger log = LogManager.getLogger(DataProvider.class);
    private static PersonDao personDao = new PersonDao();

    //curl -i
    //     -X POST
    //     -H "Authorization: Bearer {token}"
    //     -H "Content-Type: application/x-www-form-urlencoded"
    //     -H "Host: localhost:8080"
    //     -d "gender=FEMALE"
    // "localhost:8080/data/personsbatch"
    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("personsbatch")
    public Response getPersonsBatch(@FormParam("gender") Gender gender) {
        log.info("Batch of {} requested.", gender);
        try {
            List<Person> persons = personDao.getAllWhere("gender = '" + gender + "'");
            return Response.ok(
                    PersonBatchHolder.of(persons).writeJson()
            ).build();

        } catch (Exception e) {
            log.error("Get batch of gender {} failed.", gender, e);
            return Response.serverError().build();
        }
    }

}
