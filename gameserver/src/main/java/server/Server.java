package server;

import org.jetbrains.annotations.NotNull;
import server.api.ApiServlet;

public class Server {
    public static void main(@NotNull String[] args) throws Exception {
        ApiServlet.start();
    }
}
