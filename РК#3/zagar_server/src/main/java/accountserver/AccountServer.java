package accountserver;

import accountserver.api.AuthenticationFilter;
import main.ApplicationContext;
import main.Service;
import messageSystem.Address;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import utils.PropertiesReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AccountServer extends Service {
  private final static @NotNull Logger log = LogManager.getLogger(AccountServer.class);
  private final int port;

  public AccountServer(String properties) throws IOException {
    super("account_server");
    port = new PropertiesReader(properties).getIntProperty("accountServerPort");
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

    log.info(getAddress() + " started on port " + port);
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(@NotNull String[] args) throws Exception {
      new AccountServer("src/main/resources/config.properties").startApi();
  }

  @Override
  public void run() {
    startApi();
    while (true) {
      ApplicationContext.instance().get(MessageSystem.class).execForService(this);
    }
  }
}
