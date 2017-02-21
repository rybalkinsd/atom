package accountserver;

import main.ApplicationContext;
import main.Service;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by eugene on 10/7/16.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AccountServer extends Service {
  private final org.eclipse.jetty.server.Server jettyServer;
  private final String rootPath;
  private final int port;

  private static final Logger LOG = org.apache.logging.log4j.LogManager.getLogger(AccountServer.class);


  public AccountServer() {
    this(8080,"/*");
  }

  public AccountServer(Integer port) {
    this(port, "/*");
  }

  public AccountServer(Integer port, String rootPath) {
    super("account_server");
    this.jettyServer = new org.eclipse.jetty.server.Server(port);
    this.rootPath = rootPath;
    this.port = port;
  }

  public void run(){
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    ServletHolder jersey = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, rootPath);

    jersey.setInitOrder(0);
    jersey.setInitParameter("jersey.config.server.provider.packages", "accountserver/api");

    jettyServer.setHandler(context);

    try {
      jettyServer.start();
      LOG.info(String.format("server started at %d", port));
      jettyServer.join();
    } catch (InterruptedException e) {
      LOG.fatal("Account server unexpectedly interrupted: " + e.getMessage());
      System.exit(1);
    } catch (Exception e) {
      LOG.fatal(e.getMessage());
      System.exit(1);
    }

    while (true) {
      try {
        ApplicationContext.instance().get(MessageSystem.class).execOneForService(this);
      } catch (InterruptedException e) {
        LOG.fatal("Account server unexpectedly interrupted: " + e.getMessage());
        return;
      }
    }
  }

  public static void main(String[] args){
    new AccountServer().run();
  }
}
