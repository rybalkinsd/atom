package ru.atom.dbhackaton.server.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.server.AuthResources;

import java.security.Key;

/**
 * Created by dmitriy on 26.03.17.
 */
public class Token {
    private static final Logger log = LogManager.getLogger(Token.class);
    private static String token;
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
