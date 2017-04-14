package ru.atom.dbhackaton.service;

/**
 * Created by BBPax on 13.04.17.
 */
public class AuthException extends Exception{
    public AuthException(String login) {
        super(login);
    }
}
