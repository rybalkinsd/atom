package ru.atom.dbhackaton.server.service;

/**
 * Created by dmbragin on 4/16/17.
 */
public class AuthException extends Exception {
    public AuthException(String msg){
        super(msg);
    }
}
