package model.server;

import org.jetbrains.annotations.NotNull;
import model.server.api.ApiServlet;

public class Server {
    public static void main(@NotNull String[] args) throws Exception {
        ApiServlet.start();
    }
}
