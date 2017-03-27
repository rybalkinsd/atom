package ru.atom.base;


/**
 * Created by mkai on 3/26/17.
 */
public class User {
    public enum Status {
        ONLINE,
        OFFLINE
    }

    private String name;
    private String password;
    //private Status status;
    private Token token;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
        //status = Status.OFFLINE;
        token = null;
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    //public void setStatus(Status status) {
    //    this.status = status;
    //}

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
    //    if (o == null || (getClass() != o.getClass() && String.class != o.getClass())) return false;
    //
    //    if (getClass() == o.getClass()) {
    //        User user = (User) o;
    //        return name.equals(user.name);
    //    }
    //    if (String.class == o.getClass()) {
    //        String userName = (String) o;
    //        return name.equals(userName);
    //    }
    //
    //    return false;
    //}
    //
    //@Override
    //public int hashCode() {
    //    int result = name.hashCode();
    //    result = 31 * result + password.hashCode();
    //    result = 31 * result + (status != null ? status.hashCode() : 0);
    //    result = 31 * result + (token != null ? token.hashCode() : 0);
    //    return result;
    //}
}
