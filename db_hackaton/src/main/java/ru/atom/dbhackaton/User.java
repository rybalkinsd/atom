package ru.atom.dbhackaton;

/**
 * Created by Western-Co on 26.03.2017.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registered_users", schema = "game")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @Column(name = "password", nullable = false, length = 60)
    private int password;

    public User(String name, String password) {
        this.name = name;
        this.password = password.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        User other = (User) o;
        if (!name.equals(other.getName())) return false;
        if (password != (other.getPassword())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + getPassword();
    }

    public String getInJson() {
        String json = "{ " +
                "\"name\":\"" +  getName() + "\", " +
                "\"password\":\"" +  getPassword() + "\" " +
                "}";
        return json;
    }
}
