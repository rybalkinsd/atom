package ru.atom.resource;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Юля on 27.03.2017.
 */
public class User {
    private String name;
    private byte[] hashPassword;


    public User(String name, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.name = name;
        hashPassword = HashCalculator.calcHash(password.getBytes());
    }

    public String getName() {
        return name;
    }

    public byte[] getHash() {
        return hashPassword;
    }


    @Override
    public String toString() {
        return getName();
    }
}
