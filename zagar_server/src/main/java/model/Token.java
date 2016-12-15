package model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by sl on 19.10.2016.
 */

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    private Long id;
    @Column(nullable = false)
    private Date got;
    private Long id_user;

    public Token(@NotNull Long token){
        this.id = token;
        got=new Date();
    }

    public Token() { }

    @NotNull
    public Long getToken() {
        return new Long(id);
    }

    @Override
    public int hashCode() {
        return (int)(id % Integer.MAX_VALUE);
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(getClass() == obj.getClass()))
            return false;
        Token tmp = (Token) obj;
        if(tmp.id.equals(this.id))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static Token MakeToken() {
        return  new Token(ThreadLocalRandom.current().nextLong());
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
}
