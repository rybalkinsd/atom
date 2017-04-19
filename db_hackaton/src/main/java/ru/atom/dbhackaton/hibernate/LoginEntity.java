package ru.atom.dbhackaton.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kinetik on 12.04.17.
 */
@Entity
@Table(name = "login", schema = "chat", catalog = "chatdb_atom1")
public class LoginEntity {

    @OneToOne
    private RegistredEntity user;

    public RegistredEntity getUser() {
        return user;
    }

    public void setUser(RegistredEntity user) {
        this.user = user;
    }

    @Id
    @Column(name = "userId")
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token", nullable = false, length = 100)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginEntity that = (LoginEntity) o;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
