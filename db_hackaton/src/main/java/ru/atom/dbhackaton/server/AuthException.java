package ru.atom.dbhackaton.server;

/**
 * Created by konstantin on 12.04.17.
 */
public class AuthException extends Exception {
    public AuthException(String login) {
        super(login);
    }
}
