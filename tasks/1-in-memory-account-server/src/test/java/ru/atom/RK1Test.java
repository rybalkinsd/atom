package ru.atom;

import okhttp3.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class RK1Test {
    private static final Logger log = LogManager.getLogger(RK1Test.class);

    private static String MY_NAME_IN_CHAT = "serge";
    private static String MY_PASSWORD = "blabla";
    private static String MY_PASSWORD_FALSE = "blabla1";

    static Server jettyServer;

    /*
     * @BeforeClass public static void initialize() throws Exception {
     * jettyServer = HttpServer.newServer(); jettyServer.start();
     * System.out.println("server started"); }
     * 
     * @AfterClass public static void close() throws Exception {
     * jettyServer.stop(); System.out.println("server stopped"); }
     */

    @Test
    public void login() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        log.info("server started");

        Response response = RK1Client.login(MY_NAME_IN_CHAT, MY_PASSWORD);
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 200);

        jettyServer.stop();
        log.info("server stopped");
    }

    @Test
    public void register() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        log.info("server started");

        Response response = RK1Client.register(MY_NAME_IN_CHAT, MY_PASSWORD);
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 200);

        jettyServer.stop();
        log.info("server stopped");
    }

    @Test
    public void viewOnline() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        log.info("server started");

        Response response = RK1Client.viewOnline();
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 200);

        jettyServer.stop();
        log.info("server stopped");

    }

    /*
     * @Test public void logout() throws Exception {
     * 
     * jettyServer = HttpServer.newServer(); jettyServer.start();
     * log.info("server started");
     * 
     * Response response = RK1Client.logout(); System.out.println("[" + response
     * + "]"); System.out.println(); Assert.assertTrue(response.code() == 500);
     * 
     * jettyServer.stop(); log.info("server stopped"); }
     */

    /*
     * @Test public void logout() throws Exception {
     * 
     * jettyServer = HttpServer.newServer(); jettyServer.start();
     * System.out.println("server started");
     * 
     * Response response = RK1Client.register(MY_NAME_IN_CHAT, MY_PASSWORD,
     * MY_PASSWORD); response.body().string();
     * 
     * //Response response = RK1Client.logout(); System.out.println("[" +
     * response + "]"); System.out.println(); Assert.assertTrue(response.code()
     * == 401);
     * 
     * jettyServer.stop(); System.out.println("server stopped");
     * 
     * }
     */
}
