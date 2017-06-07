package ru.atom.dbhackaton.client;

import okhttp3.Response;

import javax.ws.rs.core.Response.Status;
import java.util.List;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public interface RestClient {
    public int register(String user, String password);

    public Response login(String user, String password);

    public List<String> getOnline();

    public Response logout(Long token);
}
