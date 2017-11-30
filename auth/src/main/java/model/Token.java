package model;

import javax.persistence.*;

@Entity
@Table(name = "token", schema = "auth")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST, targetEntity = User.class)
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Token setId(Integer id) {
        this.id = id;
        return this;
    }

    public Token setUser(User user) {
        this.user = user;
        return this;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", user=" + user +
                ", tokenHash='" + token + '\'' +
                '}';
    }
}
