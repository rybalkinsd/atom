package ru.atom.server.like;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.data.Like;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/like")
public class LikeServlet {
    private static final Logger log = LogManager.getLogger(LikeServlet.class);
    private static List<Like> likes = new ArrayList<>();

    //curl -i
    //     -X POST
    //     -H "Content-Type: application/x-www-form-urlencoded"
    //     -H "Host: localhost:8080"
    //     -d "gender=FEMALE"
    // "localhost:8080/data/personsbatch"
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("like")
    public Response like(@FormParam("source") Integer source, @FormParam("target") Integer target) {
        log.info(source + " <3 " + target);
        likes.add(new Like(source, target));
        StringBuilder stringBuffer = new StringBuilder();
        for (Like like: likes){
            stringBuffer.append(like.getSource()).append(" <3 ").append(like.getTarget()).append("\n");
        }
        return Response.ok(stringBuffer.toString()).build();
    }

}
