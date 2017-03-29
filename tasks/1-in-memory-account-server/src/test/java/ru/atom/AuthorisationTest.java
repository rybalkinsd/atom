package ru.atom;



import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

import ru.atom.server.RegisterJersey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;




/**
 * Created by Fella on 29.03.2017.
 */
public class AuthorisationTest {


    @Test
    public void  register() throws Exception {
        RegisterJersey rj = new RegisterJersey();
        Response response = rj.register("ela","1234gg");
        assertEquals(200,response.getStatus());
    }

    @Test
    public void register2() throws Exception {
        assertTrue(Users.isContainsName("ela"));
    }

}
