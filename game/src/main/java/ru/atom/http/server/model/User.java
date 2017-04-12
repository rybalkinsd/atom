package ru.atom.http.server.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zarina on 23.03.17.
 */
public class User {
    private String name;
    private transient String password;

    public User(String name, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.name = name;
        this.password = generatePassword(password);
    }

    private String generatePassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest crypt = MessageDigest.getInstance("SHA-256");
        crypt.reset();
        crypt.update((name + password).getBytes("UTF-8"));

        return new BigInteger(1, crypt.digest()).toString(16);
    }

    public String getName() {
        return name;
    }

    public boolean changePassword(String oldPassword, String newPassword)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (this.validPassword(oldPassword)) {
            this.password = generatePassword(newPassword);
            return true;
        }
        return false;
    }

    public boolean validPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return this.password.equals(generatePassword(password));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getName();
    }


}
