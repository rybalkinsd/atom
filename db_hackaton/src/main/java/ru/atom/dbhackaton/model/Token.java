package ru.atom.dbhackaton.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Encoded;
import java.security.Key;

/**
 * Created by dmitriy on 26.03.17.
 */
@Entity
@Table(name = "token", schema = "game")
public class Token {
    private static final Logger log = LogManager.getLogger(Token.class);

    @Id
    private String token;

    @OneToOne
    private User user;

    public static final Key key = MacProvider.generateKey();

    public Token(User user) {
        Claims claims = Jwts.claims();
        claims.put("user", user.getName());
        claims.put("password", user.getPassword());
        this.token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() { return token; }

    public void setToken(String token) {
        this.token = token;
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
        return token;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
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
