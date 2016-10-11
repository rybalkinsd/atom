package ru.atom.tinder.server;

import org.jetbrains.annotations.NotNull;
import ru.atom.tinder.server.api.ApiServlet;

/**
 * Created by s.rybalkin on 28.09.2016.
 */
public class Server {
    public static void main(@NotNull String[] args) throws Exception {
        ApiServlet.start();
    }
}
