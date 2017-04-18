package ru.atom.dbhackaton.mm;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ru.atom.dbhackaton.server.CrossBrowserFilter;
import ru.atom.dbhackaton.server.dao.Database;

public class MMserver {
	public static void main(String[] args) throws Exception {
		Database.setUp();
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { createChatContext() });

		Server jettyServer = new Server(8081);
		jettyServer.setHandler(contexts);

		jettyServer.start();
	}

	private static ServletContextHandler createChatContext() {
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/mm/*");
		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "ru.atom.dbhackaton.mm");

		jerseyServlet.setInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters",
				CrossBrowserFilter.class.getCanonicalName());

		return context;
	}
}
