package ru.atom.base;


public class User {
    private String name;
    private String password;
    private Token token;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
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

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
