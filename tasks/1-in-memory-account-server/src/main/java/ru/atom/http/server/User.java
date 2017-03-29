package ru.atom.http.server;

/**
 * Created by hpechka on 29.03.2017.
 */
public class User {
    private String userName;
    private String userPassword;

    User(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserPassword(){
        return userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        return userPassword != null ? userPassword.equals(user.userPassword) : user.userPassword == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        return result;
    }
}
