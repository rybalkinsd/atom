import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class AccountServer {
  public static void main(String[] args) {
    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");

    org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8080);
    server.setHandler(context);

    ServletHolder jerseyServlet = context.addServlet(
        ServletContainer.class, "/*");
    jerseyServlet.setInitOrder(0);

    jerseyServlet.setInitParameter(
        "com.sun.jersey.config.property.packages",
        "api"
    );

    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
