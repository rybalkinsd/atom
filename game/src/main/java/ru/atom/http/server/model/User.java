package ru.atom.http.server.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Entity
@Table(name = "user", schema = "game")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", nullable = false, length = 20, unique = true)
    private String name;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt;


    private String generatePassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest crypt = MessageDigest.getInstance("SHA-256");
        crypt.reset();
        crypt.update((name + password).getBytes("UTF-8"));

        return new BigInteger(1, crypt.digest()).toString(16);
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        this.password = generatePassword(password);
        return this;
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
