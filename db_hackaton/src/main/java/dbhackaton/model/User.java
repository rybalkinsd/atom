package dbhackaton.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ilnur on 12.04.17.
 */


@Entity
@Table(name = "usser", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String token;


    public User(String name, String password) {
        this.setName(name);
        this.setPassword(password);
        this.token = null;
        this.id = null;
    }

    public User() {
        this.setPassword(null);
        this.setName(null);
        this.token = null;
        this.id = null;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }


    public void setToken() {
        this.token = new Token(this.name, this.password).toString();
    }

    public String getToken() {
        return token;
    }

    public void getLogout() {
        this.token = null;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }




    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + name + '\'' +
                '}';
    }
}

