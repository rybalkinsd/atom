package ru.atom.dbhackaton.server.service;

/**
 * @author apomosov
 * @since 05.04.17
 */
public class AuthException extends Exception {
    public AuthException(String login) {
        super(login);
    }
}
