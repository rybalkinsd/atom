package ru.atom;

import okhttp3.Response;

import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Test;

//@Ignore
public class ChatClientTest {
    private static String MY_NAME_IN_CHAT = "serge";
    private static String MY_PASSWORD = "blabla";
    private static String MY_PASSWORD_FALSE = "blabla1";
    //private static String MY_MESSAGE_TO_CHAT = "hello chat";

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
    public void login1() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        System.out.println("server started");

        Response response = ChatClient.login(MY_NAME_IN_CHAT, MY_PASSWORD);
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 400);

        jettyServer.stop();
        System.out.println("server stopped");
    }

    @Test
    public void register() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        System.out.println("server started");

        Response response = ChatClient.register(MY_NAME_IN_CHAT, MY_PASSWORD, MY_PASSWORD_FALSE);
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 400);

        jettyServer.stop();
        System.out.println("server stopped");
    }

    @Test
    public void viewChat() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        System.out.println("server started");

        Response response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 401);

        jettyServer.stop();
        System.out.println("server stopped");
    }

    @Test
    public void viewOnline() throws Exception {

        jettyServer = HttpServer.newServer();
        jettyServer.start();
        System.out.println("server started");

        Response response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        System.out.println();
        Assert.assertTrue(response.code() == 401);

        jettyServer.stop();
        System.out.println("server stopped");

    }

    /*
     * @Test public void say() throws Exception {
     * 
     * jettyServer = HttpServer.newServer(); jettyServer.start();
     * System.out.println("server started");
     * 
     * Response response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
     * System.out.println("[" + response + "]"); System.out.println();
     * Assert.assertTrue(response.code() == 401);
     * 
     * jettyServer.stop(); System.out.println("server stopped");
     * 
     * }
     * 
     * @Test public void say2() throws Exception {
     * 
     * jettyServer = HttpServer.newServer(); jettyServer.start();
     * System.out.println("server started");
     * 
     * Response response1 = ChatClient.register(MY_NAME_IN_CHAT, MY_PASSWORD,
     * MY_PASSWORD); System.out.println("[" + response1 + "]");
     * System.out.println(); Assert.assertTrue(response1.code() == 200);
     * 
     * Response response2 = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
     * System.out.println("[" + response2 + "]"); System.out.println();
     * Assert.assertTrue(response2.code() == 200);
     * 
     * jettyServer.stop(); System.out.println("server stopped");
     * 
     * }
     */
}
