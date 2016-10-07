package ru.atom.server.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.Gender;
import ru.atom.model.Image;
import ru.atom.model.Location;
import ru.atom.model.person.Person;
import ru.atom.model.person.PersonBatchHolder;
import ru.atom.server.auth.Authentication;
import ru.atom.server.auth.Authorized;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Path("/data")
public class DataProvider {
    private static final Logger log = LogManager.getLogger(DataProvider.class);
    private static PersonBatchHolder LADIES;
    private static PersonBatchHolder MEN;

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
    public Response getPersonsBatch(@FormParam("gender") Gender gender) throws IOException {
        log.info("Batch of {} requested.", gender);
        return Response.ok(
                gender == Gender.FEMALE
                ? LADIES.writeJson()
                : MEN.writeJson()
            ).build();
    }

    static {
        try {
            LADIES = PersonBatchHolder.of(
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Алла Пугачева")
                            .setGender(Gender.FEMALE)
                            .setAge(67)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/commons/3/36/%D0%90%D0%BB%D0%BB%D0%B0_%D0%9F%D1%83%D0%B3%D0%B0%D1%87%D1%91%D0%B2%D0%B0_%D0%BD%D0%B0_%D1%81%D1%8A%D1%91%D0%BC%D0%BA%D0%B0%D1%85_%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D1%8B_%D0%A4%D0%90%D0%9A%D0%A2%D0%9E%D0%A0_%D0%90_%282012%29.jpg"), 800, 1200))
                            .setInstagramUrl(new URL("https://www.instagram.com/orfey75alla/"))
                            .setLocation(new Location(55.75, 37.616667)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Sasha Grey")
                            .setGender(Gender.FEMALE)
                            .setAge(28)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/commons/d/dc/Sasha_Grey_2010.jpg"), 1152, 1728))
                            .setInstagramUrl(new URL("https://www.instagram.com/sashagrey/"))
                            .setLocation(new Location(38.575278, -121.486111)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Marge Simpson")
                            .setGender(Gender.FEMALE)
                            .setAge(34)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/en/0/0b/Marge_Simpson.png"), 300, 463))
                            .setInstagramUrl(new URL("https://www.instagram.com/margebouviersimpson/"))
                            .setLocation(new Location(39.783333, -89.650278)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Sailor 'Pretty guardian' Moon")
                            .setGender(Gender.FEMALE)
                            .setAge(14)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/az/8/8d/Seylor_Mun.jpg"), 418, 629))
                            .setInstagramUrl(new URL("https://www.instagram.com/sailormoon_sc/"))
                            .setLocation(new Location(35.666667, 138.566667)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Queen 'mommy' Elizabeth II")
                            .setGender(Gender.FEMALE)
                            .setAge(90)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/commons/5/50/Queen_Elizabeth_II_March_2015.jpg"), 1820, 2400))
                            .setInstagramUrl(new URL("https://www.instagram.com/queen_elizabeth_fanpage/"))
                            .setLocation(new Location(51.507222, -0.1275))
                    );
            MEN = PersonBatchHolder.of(
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Homer 'Jay' Simpson")
                            .setGender(Gender.MALE)
                            .setAge(38)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/ru/b/bd/Homer_Simpson.png"), 212, 347))
                            .setInstagramUrl(new URL("https://www.instagram.com/mr.homersimpson/"))
                            .setLocation(new Location(38.783333, -88.650278)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Cristiano Ronaldo")
                            .setGender(Gender.MALE)
                            .setAge(31)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/commons/0/0f/Rus-Por2012_%2816%29.jpg"), 552, 733))
                            .setInstagramUrl(new URL("https://www.instagram.com/cristiano/"))
                            .setLocation(new Location(32.65, -16.916667)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("James Gosling")
                            .setGender(Gender.MALE)
                            .setAge(61)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/commons/0/00/James_Gosling_2005.jpg"), 372, 512))
                            .setInstagramUrl(null)
                            .setLocation(new Location(51.045, -114.057222)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Justin Bieber")
                            .setGender(Gender.MALE)
                            .setAge(22)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/commons/2/23/Justin_Bieber_NRJ_Music_Awards_2012.jpg?uselang=ru"), 494, 702))
                            .setInstagramUrl(new URL("https://www.instagram.com/explore/tags/justinbieber/"))
                            .setLocation(new Location(42.982516, -81.253974)),
                    new Person()
                            .setId(UUID.randomUUID())
                            .setName("Hannibal Lecter")
                            .setGender(Gender.MALE)
                            .setAge(78)
                            .setImage(new Image(new URL("https://upload.wikimedia.org/wikipedia/ru/c/ca/Hannibal_Lecter.jpg"), 304, 380))
                            .setInstagramUrl(new URL("https://www.instagram.com/explore/tags/anthonyhopkins/"))
                            .setLocation(new Location(51.58, -3.81))
            );
        } catch (MalformedURLException ignored) { }
    }
}
