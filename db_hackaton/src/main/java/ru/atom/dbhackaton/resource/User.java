package ru.atom.dbhackaton.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by BBPax on 24.03.17.
 */
// TODO: 14.04.17   нужна еще дата регистрации!
@Entity
@Table(name = "user", schema = "game")
public class User {
    private static final Logger log = LogManager.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @Column(name = "password", nullable = false, length = 40)
    private String password;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = generatePassword(password);
        return this;
    }

    // TODO: 13.04.17 нужно потом для хеширования паролей
    private String generatePassword(String password) {
        return password;
    }

    public boolean validPassword(String password) {
        return this.password.equals(generatePassword(password));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login != null ? login.equals(user.login) : user.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", login='" + login + '\'' +
                '}';
    }


}
