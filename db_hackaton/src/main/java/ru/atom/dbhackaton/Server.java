package ru.atom.dbhackaton;

/**
 * Created by vladfedorenko on 26.03.17.
 */

import ru.atom.auth.ApiServlet;
import org.jetbrains.annotations.NotNull;
import ru.atom.client.RestClient;
import ru.atom.client.RestClientImpl;


public class Server {
    public static void main(String[] args) throws Exception {
        ApiServlet.start(false);
    }
}