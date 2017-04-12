package ru.atom.dbhackaton;

/**
 * Created by vladfedorenko on 26.03.17.
 */

import ru.atom.dbhackaton.auth.ApiServlet;
import org.jetbrains.annotations.NotNull;
import ru.atom.dbhackaton.client.RestClient;
import ru.atom.dbhackaton.client.RestClientImpl;


public class Server {
    public static void main(String[] args) throws Exception {
        ApiServlet.start(false);
    }
}