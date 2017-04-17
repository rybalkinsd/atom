package ru.atom.server;

import ru.atom.dao.TokenDao;
import ru.atom.dao.UserDao;
import ru.atom.object.Token;
import ru.atom.object.User;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Fella on 17.04.2017.
 */
public class Experiment {
    static UserDao userDao = new UserDao();
    static TokenDao tokenDao = new TokenDao();

    public static void main(String[] args) {
        RegisterJersey rj = new RegisterJersey();
        rj.logout("Behaviour 8452333647438674299");
    }

}