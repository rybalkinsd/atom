package ru.atom.rk1;

import org.junit.Test;
import ru.atom.model.data.Gender;
import ru.atom.model.data.person.Person;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by s.rybalkin on 04.10.2016.
 */
public class RK1Test {
  private static final String user = "atomuser";
  private static final String pass = "atompass";
  private static final String wronguser = "wronguser";
  private static final String wrongpass = "wronpass";
  private static final String newuser = "newatomuser";
  private static final Long wrongtoken = 298201984L;

  private RK1Client client = new RK1Client();

  @Test
  public void register() throws Exception {
    System.out.println("REGISTER with user = " + user + " password = " + pass);
    assertTrue(client.register(user, pass));
  }

  @Test
  public void login() throws Exception {
    System.out.println("LOGIN with user = " + user + " password = " + pass);
    Long token = client.login(user, pass);
    System.out.println("token received = " + token);
    assertNotNull(token);
  }

  @Test
  public void logout() throws Exception {
    //LOGIN
    System.out.println("LOGIN with user = " + user + " password = " + pass);
    Long token = client.login(user, pass);
    System.out.println("token received = " + token);
    assertNotNull(token);

    //LOGOUT
    System.out.println("LOGOUT with WRONG token " + wrongtoken);
    assertFalse(client.logout(wrongtoken));

    System.out.println("LOGOUT with token " + token);
    assertTrue(client.logout(token));
  }

  @Test
  public void rename() throws Exception {
    //LOGIN
    System.out.println("LOGIN with user = " + user + " password = " + pass);
    Long token = client.login(user, pass);
    System.out.println("token received = " + token);
    assertNotNull(token);

    //RENAME
    System.out.println("RENAME with WRONG token " + wrongtoken);
    assertFalse(client.rename(wrongtoken, newuser));

    System.out.println("RENAME with token " + token);
    assertTrue(client.rename(token, newuser));
  }

  @Test
  public void users() throws Exception {
    //LOGIN
    System.out.println("LOGIN with user = " + user + " password = " + pass);
    Long token = client.login(user, pass);
    System.out.println("token received = " + token);
    assertNotNull(token);

    //RENAME
    System.out.println("RENAME with token " + token);
    assertTrue(client.rename(token, newuser));

    //ONLINE
    System.out.println("GET USERS");
    String online = client.online();
    System.out.println(online);
    assertTrue(online.contains(newuser));
    assertFalse(online.contains(user));
  }

  @Test
  public void testAll() throws Exception {
    //REGISTER
    System.out.println("REGISTER with user = " + user + " password = " + pass);
    assertTrue(client.register(user, pass));

    //LOGIN
    System.out.println("LOGIN with WRONG user = " + wronguser + " password = " + wrongpass);
    Long noTokenExpected1 = client.login(wronguser, pass);
    System.out.println("token received = " + noTokenExpected1);
    assertNull(noTokenExpected1);

    System.out.println("LOGIN with user = " + user + " WRONG password = " + wrongpass);
    Long noTokenExpected2 = client.login(user, wrongpass);
    System.out.println("token received = " + noTokenExpected2);
    assertNull(noTokenExpected2);

    System.out.println("LOGIN with user = " + user + " password = " + pass);
    Long token = client.login(user, pass);
    System.out.println("token received = " + token);
    assertNotNull(token);

    //LOGOUT
    System.out.println("LOGOUT with WRONG token " + wrongtoken);
    assertFalse(client.logout(wrongtoken));

    System.out.println("LOGOUT with token " + token);
    assertTrue(client.logout(token));

    //RENAME
    System.out.println("LOGIN with user = " + user + " password = " + pass);
    token = client.login(user, pass);
    System.out.println("token received = " + token);
    assertNotNull(token);

    System.out.println("RENAME with WRONG token " + wrongtoken);
    assertFalse(client.rename(wrongtoken, newuser));

    System.out.println("RENAME with token " + token);
    assertTrue(client.rename(token, newuser));

    //ONLINE
    System.out.println("GET USERS");
    String online = client.online();
    System.out.println(online);
    assertTrue(online.contains(newuser));
    assertFalse(online.contains(user));
  }
}