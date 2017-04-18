package ru.atom.dbhackaton.server.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user", schema = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String name;

    @Column(name = "passwd", nullable = false, length = 20)
    private String password;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;


    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.registrationDate = new Date(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
