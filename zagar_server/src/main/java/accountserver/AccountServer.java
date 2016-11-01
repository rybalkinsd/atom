package accountserver;

import accountserver.api.AuthenticationFilter;
import main.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;


public class AccountServer extends Service {
  private final static @NotNull Logger log = LogManager.getLogger(AccountServer.class);
  private final int port;

  public AccountServer(int port) {
    super("account_server");
    this.port = port;
    startApi();
  }

  private void startApi() {
    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");

    org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
    server.setHandler(context);

    ServletHolder jerseyServlet = context.addServlet(
        org.glassfish.jersey.servlet.ServletContainer.class, "/*");
    jerseyServlet.setInitOrder(0);

    jerseyServlet.setInitParameter(
        "jersey.config.server.provider.packages",
        "accountserver.api"
    );

    jerseyServlet.setInitParameter(
        "com.sun.jersey.spi.container.ContainerRequestFilters",
        AuthenticationFilter.class.getCanonicalName()
    );

    log.info(getName() + " started on port " + port);
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(@NotNull String[] args) throws Exception {
    new AccountServer(8080).startApi();
  }
}
