package ru.atom.rk01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Created by dmbragin on 3/28/17.
 */
public class User implements Serializable {
    private static final Logger log = LogManager.getLogger();
    private String login;
    private String passwd;

    public User(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
        log.info("Create object with login = {} and passwd = {}", this.login, this.passwd);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        return passwd != null ? passwd.equals(user.passwd) : user.passwd == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        return result;
    }
}
