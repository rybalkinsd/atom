import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class AccountServer {
  public static void main(String[] args) {
    Server server = new Server(8080);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/webapi/*");
    jerseyServlet.setInitOrder(1);
    jerseyServlet.setInitParameter("jersey.config.server.provider.packages","com.example");

    ServletHolder staticServlet = context.addServlet(HWResource.class,"/*");
    staticServlet.setInitParameter("resourceBase","src/main/webapp");
    staticServlet.setInitParameter("pathInfoOnly","true");

    try
    {
      server.start();
      server.join();
    }
    catch (Throwable t)
    {
      t.printStackTrace(System.err);
    }
  }
}
