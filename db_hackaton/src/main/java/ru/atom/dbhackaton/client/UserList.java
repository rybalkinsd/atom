package ru.atom.dbhackaton.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladfedorenko on 29.03.17.
 */

public class UserList {
    private List<String> users = new ArrayList<String>();

    public UserList() {
    }

    public List<String> getUsers() {
        return this.users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}