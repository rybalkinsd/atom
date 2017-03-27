package ru.atom.client;

import okhttp3.Response;

/**
 * Created by kinetik on 26.03.17.
 */
public interface Client<E> {

    Response register(String name, String password);

    Response login(String name, String password);

    Response logout(String name);

    Response getUsers(String name);
}
