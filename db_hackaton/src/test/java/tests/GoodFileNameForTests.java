package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.eclipse.jetty.server.Server;
import org.postgresql.ssl.jdbc4.AbstractJdbc4MakeSSL;
import ru.atom.dbhackaton.auth.ApiServlet;
import ru.atom.dbhackaton.client.AuthClient;
import ru.atom.dbhackaton.client.MatchMakerClient;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.mm.MatchMaker;
import ru.atom.dbhackaton.mm.MatchMakerServer;
import okhttp3.Response;
import ru.atom.dbhackaton.model.TokenStorage;
import ru.atom.dbhackaton.model.User;
import ru.atom.dbhackaton.model.UserStorage;

import java.io.IOException;


/**
 * Created by ilysk on 18.04.17.
 */
@FixMethodOrder(MethodSorters.JVM)
public class GoodFileNameForTests {
    private static Server jettyServerForAuth;
    private static Server jettyServerForMM;

    @Before
    public void initAuthServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/auth/*");

        jettyServerForAuth = new Server(8080);
        jettyServerForAuth.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton"
        );

        jettyServerForAuth.start();
    }

    @Before
    public void initMatchMakerServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm");

        jettyServerForMM = new Server(8282);
        jettyServerForMM.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.mm"
        );

        jettyServerForMM.start();
    }

    @Before
    public void clearDBFromTestUsers() {
        try {
            RegistredEntity userToDrop = UserStorage.getByName("test_user");
            TokenStorage.logoutToken("test_user");
            UserStorage.dropUser(userToDrop);
        } catch (IllegalArgumentException e) {
            //nothing
        }
    }

    @Test
    public void loginWithoutRegistration() throws IOException {
        Response response = AuthClient.login("test_user", "123");
        Assert.assertTrue(response.code() == 401);
    }

    @Test
    public void logoutWithoutRegistration() {
        Response response = AuthClient.logout();
        Assert.assertTrue(response.code() == 401);
    }

    @Test
    public void joinWithoutLogin() throws IOException {
        Response response = MatchMakerClient.join("test", "111111");
        Assert.assertTrue(response.code() == 401);
    }

    @Test
    public void finishWithWrongJsonFormat() throws IOException {
        Response response = MatchMakerClient.finish("{}");
        Assert.assertTrue(response.code() == 400);
    }

    @Test
    public void registration() throws IOException {
        Assert.assertTrue(UserStorage.getByName("test_user") == null);
        Response response = AuthClient.register("test_user", "test_password");
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void registrationWhenAlsoRegistrated() throws IOException {
        Assert.assertTrue(UserStorage.getByName("test_user") != null);
        Response response = AuthClient.register("test_user", "test_password");
        Assert.assertTrue(response.code() == 406);
    }

    @Test
    public void loginAfterRegistrationWithWrongPassword() {
        Assert.assertTrue(UserStorage.getByName("test_user") != null);
        Response response = AuthClient.login("test_user", "wrong_password");
        Assert.assertTrue(response.code() == 401);
    }

    @Test
    public void loginAfterRegistration() {
        Assert.assertTrue(TokenStorage.getLoginByName("test_user") == null);
        Response response = AuthClient.login("test_user", "test_password");
        Assert.assertTrue(response.code() == 200
                && TokenStorage.getLoginByName("test_user") != null);
    }

    @Test
    public void join() throws IOException {
        Assert.assertTrue(TokenStorage.getLoginByName("test_user") != null);
        Response response = MatchMakerClient.join("user_test", "toooken");
        Assert.assertTrue(response.code() == 200
                && response.body().string().equals("wtfis.ru:8090/gs/12345"));
    }

    @Test
    public void finish() throws IOException {
        Assert.assertTrue(TokenStorage.getLoginByName("test_user") != null);
        Response response = MatchMakerClient.finish("{\\\"id\\\":1234," +
                "\\\"result\\\":{\\\"test_user\\\":15}}");
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void logout() {
        Response response = AuthClient.logout();
        Assert.assertTrue(response.code() == 200
                    && TokenStorage.getLoginByName("user_test") == null);
    }

    @After
    public void stopBothServers() throws Exception {
        jettyServerForMM.stop();
        jettyServerForAuth.stop();
    }
}
