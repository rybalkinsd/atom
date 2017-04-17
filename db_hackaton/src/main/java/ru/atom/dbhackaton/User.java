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
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "registered_users", schema = "game")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "token")
    private Long token = null;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = String.valueOf(password.hashCode());
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token){
        this.token = token;
    }

    public void setNewToken() {this.token = next.getAndAdd((new Date().getTime()));}

    private static AtomicLong next = new AtomicLong();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        User other = (User) o;
        if (!name.equals(other.getName())) return false;
        if (!password.equals(other.getPassword())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(getName().hashCode() + getPassword());
    }

    public String getInJson() {
        String json = "{ " +
                "\"name\":\"" +  getName() + "\", " +
                "\"password\":\"" +  getPassword() + "\" " +
                "}";
        return json;
    }
}
