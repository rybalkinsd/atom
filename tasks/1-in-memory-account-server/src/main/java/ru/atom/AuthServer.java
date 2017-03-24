package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

/**
 * Created by mkai on 3/24/17.
 */
public class AuthServer {
    private static final Logger logger = LogManager.getLogger(AuthServer.class);

    public static void main(String[] args) throws Exception {
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{});

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(contexts);

        jettyServer.start();
        logger.info("server start!");
    }

}
