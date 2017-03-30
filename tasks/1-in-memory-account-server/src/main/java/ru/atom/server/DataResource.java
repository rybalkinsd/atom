package ru.atom.server;

/**
 * Created by Ксения on 28.03.2017.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("data")
public class DataResource {

    //  Protocol: HTTP
    //          Path: data/users
    //          Method: GET
    //          Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
    //          Response:
    //          Code: 200
    //          Сontent-Type: application/json
    //          Body: json вида {"users" : [{User1}, {User2}, ... ]}

    @Path("/users")
    @GET
    @Produces("application/json")
    public static Response users() {
        return Response.ok(UserStorage.toJson())
                .build();
    }
}
