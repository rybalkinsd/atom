package ru.atom.bombergirl.server;

/**
 * Created by user on 12.04.2017.
 */
public class AuthException extends Exception {
    public AuthException(String login) {
        super(login);
    }
}
