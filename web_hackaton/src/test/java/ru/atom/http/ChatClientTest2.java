package ru.atom.http;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.http.server.ChatResource;

import java.io.IOException;

/**
 * Created by Fella on 22.03.2017.
 */
public class ChatClientTest2 {
    @Test
    public void post() throws IOException {
        ChatResource chatResource = new ChatResource();
        Response response = chatResource.login("1234567890123456789012345678901234567890");
        Assert.assertEquals("Too long name, sorry :(",(String)response.getEntity());
    }

}
