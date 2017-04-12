package ru.atom.dbhackaton.exeptions;

/**
 * Created by Western-Co on 12.04.2017.
 */
public class RegisterExeption extends Exception{
    public RegisterExeption(String login) {
        super(login);
    }
}
