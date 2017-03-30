package ru.atom.rk1.server;


import javax.ws.rs.core.Response;

public interface Resource {
    Response register(String name, String password);

    Response login(String name, String password);

    Response logout(String token);

    Response users();
}
