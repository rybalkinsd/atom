package ru.atom.chat.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Table(name = "user", schema = "chat")
public class User implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true, nullable = false, length = 20)
    private String login;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    // TODO create OneToMany with messages

    private Date lastDate;

    public User() {
    }

    public User(String userName, String password) {
        this.login = userName;
        this.password = password;
        if (!this.password.equals(password)) {
            throw new RuntimeException("wrong password");
        }
        this.lastDate = new Date();
    }

    public boolean login(String password) {
        boolean checking = passCheck(password);
        return checking;
    }

    public boolean passCheck(String password) {
        if (!this.password.equals(password)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public User setLastDate(Date lastDate) {
        this.lastDate = lastDate;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }


    public boolean spamCheck() {
        // если прошло < 1 секунды, спам

        if (new Date().getTime() - lastDate.getTime() < 2500) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "name:" + login + " id:" + id;
    }
}
