package ru.atom.dbhackaton;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

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
@Table(name = "", schema = "game") !!!
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "token", unique = true, nullable = false)
    private Long token;

    private static AtomicLong next = new AtomicLong();

    public Token() {
        this.token = next.getAndAdd((new Date().getTime()));
    }

    public Token(String strToken) {
        if (strToken.length() > 0) {
            this.token = Long.parseLong(strToken);
        } else {
            this.token = -1L;
        }
    }

    public static void setNext(AtomicLong next) {
        Token.next = next;
    }

    public long getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Token other = (Token) o;
        if (token != (other.getToken())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(getToken());
    }
}
