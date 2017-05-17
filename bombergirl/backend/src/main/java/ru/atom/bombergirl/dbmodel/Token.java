package ru.atom.bombergirl.dbmodel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.security.Key;

/**
 * Created by dmitriy on 26.03.17.
 */
@Entity
@Table(name = "token", schema = "game")
public class Token {
    private static final Logger log = LogManager.getLogger(Token.class);

    @Id
    private String value;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    public Token() {}

    public static final Key key = MacProvider.generateKey();

    public Token(User user) {
        Claims claims = Jwts.claims();
        claims.put("user", user.getName());
        claims.put("password", user.getPassword());
        this.value = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Token(String token) {
        this.value = token;
    }

    public String getToken() {
        return value;
    }

    public void setToken(String token) {
        this.value = token;
    }

    public Token setUser(User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Token token = (Token)obj;
        return this.hashCode() == token.hashCode();
    }
}
