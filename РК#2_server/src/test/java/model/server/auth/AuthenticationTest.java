package model.server.auth;

import model.server.auth.Authentication;
import model.server.auth.AuthenticationFilter;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by venik on 04.11.16.
 */
public class AuthenticationTest {

    @Test
    public void validateToken() throws Exception {
            Authentication.validateToken("{\"id\":83,\"date\":1478277630646}");
    }

}
