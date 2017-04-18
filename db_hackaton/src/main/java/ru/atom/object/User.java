package ru.atom.object;

import java.util.Date;

/**
 * Created by Fella on 26.03.2017.
 */
public class User {

    private String login;
    private String password;
    private Date registrationDate;

    private int idUser;
    /*  private int idMatch;*/
    /* private float lifeTime;*/


    public User() {
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
    }


    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public User setIdUser(int idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (password != user.password) return false;
        return login != null ? login.equals(user.login) : user.login == null;
    }


    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }


    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + getIdUser();
        return result;
    }
}
