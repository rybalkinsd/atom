package ru.atom.object;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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


    public boolean isThatPassword(String password) {
        return password.equals(md5Custom(this.password));
    }


    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    private static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
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
        return md5Custom(password);
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
