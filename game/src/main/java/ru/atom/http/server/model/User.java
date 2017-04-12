package ru.atom.http.server.model;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "user", schema = "game")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", nullable = false, length = 20, unique = true)
    private String name;

    @Column(name = "password", nullable = false, length = 255)
    private transient String password;

    private String generatePassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest crypt = MessageDigest.getInstance("SHA-256");
        crypt.reset();
        crypt.update((name + password).getBytes("UTF-8"));

        return new BigInteger(1, crypt.digest()).toString(16);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        this.password = generatePassword(password);
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
