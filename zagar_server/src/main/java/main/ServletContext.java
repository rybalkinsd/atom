package main;

import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Created by apomosov on 13.06.16.
 */
public class ServletContext {
    private static ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    public static ServletContextHandler getInstance() {
        return context;
    }
}
