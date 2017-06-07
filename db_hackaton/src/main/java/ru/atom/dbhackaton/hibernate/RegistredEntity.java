package ru.atom.dbhackaton.hibernate;

import org.hibernate.annotations.GenerationTime;
import ru.atom.dbhackaton.mm.UserGameResult;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by kinetik on 12.04.17.
 */
@Entity
@Table(name = "registred", schema = "chat", catalog = "chatdb_atom1")
public class RegistredEntity {

    public RegistredEntity() {
    }

    public RegistredEntity(String login, String password, Timestamp regdate) {
        this.login = login;
        this.password = password;
        this.regdate = regdate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 100)
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 100)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "regdate", nullable = true)
    private Timestamp regdate;

    public Timestamp getRegdate() {
        return regdate;
    }

    public void setRegdate(Timestamp regdate) {
        this.regdate = regdate;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private LoginEntity token;

    public LoginEntity getToken() {
        return token;
    }

    public void setToken(LoginEntity token) {
        this.token = token;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserGameResult> gameResults;

    public Set<UserGameResult> getGameResults() {
        return this.gameResults;
    }

    public void setGameResults(Set<UserGameResult> gameResults) {
        this.gameResults = gameResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistredEntity that = (RegistredEntity) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (regdate != null ? !regdate.equals(that.regdate) : that.regdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (regdate != null ? regdate.hashCode() : 0);
        return result;
    }
}
