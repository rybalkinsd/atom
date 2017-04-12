package ru.atom.dbhackaton.model;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public class User {
    private String name;
    private String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return this.name;
    }

    public boolean checkPass(String newPass) {
        return this.pass.equals(newPass);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        try {
            return ((User) o).getName().equals(this.name);
        } catch (ClassCastException e) {
            return false;
        }
    }
}

