package ru.atom.dbhackaton.server.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.atom.dbhackaton.server.resource.HashCalculator;


@Entity
@Table(name = "user", schema = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String name;

    @Column(name = "password", unique = false, nullable = false, length = 30)
    private String password;

    //private byte[] hashPassword;

    public User() {
    }

    public User(String name, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.name = name;
        this.password = password;
        //hashPassword = HashCalculator.calcHash(password.getBytes());
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return password;
    }

    /*
    public byte[] getHash() {
        return hashPassword;
    }
    */

    @Override
    public String toString() {
        return getName();
    }
}
