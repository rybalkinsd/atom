package ru.atom.server;

import ru.atom.server.api.ApiServlet;

/**
 * Created by s.rybalkin on 28.09.2016.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ApiServlet.start();
    }

}
