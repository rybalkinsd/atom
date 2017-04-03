package main;

import accounts.AccountService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.GetUsers;
import servlets.LogOutServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;


/**
 * Created by ilnur on 29.03.17.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/auth/login");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/auth/register");
        context.addServlet(new ServletHolder(new LogOutServlet(accountService)), "/auth/logout");
        context.addServlet(new ServletHolder(new GetUsers(accountService)), "/data/users");


        ResourceHandler resourcehandler = new ResourceHandler();
        resourcehandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourcehandler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);


        server.start();
        server.join();
    }
}
