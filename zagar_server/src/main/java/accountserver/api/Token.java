package accountserver.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 24.10.2016.
 */
@Entity
@Table(name = "token")
public class Token {
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private Long token;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int userId;

    public Token() {
        token = ThreadLocalRandom.current().nextLong();
        date = new Date();
    }

    public Token(Long token){
        this.token = token;
        date = new Date();
    }

    public Token setDate (Date date){
        this.date = date;
        return this;
    }

    public Token setUserId(int id){
        this.userId = id;
        return this;
    }

    @Override
    public int hashCode (){
        return Long.hashCode(token);
    }

    public Token(String rawToken){
        rawToken = rawToken.substring("Bearer".length()).trim();
        token = Long.parseLong(rawToken);
        date = new Date();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (!Token.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Token other = (Token) obj;
        if (!this.token.equals(other.getToken())) {
            return false;
        }
        return true;
    }

    public Long getToken(){
        return token;
    }

    public int getUserId() { return userId; }

    public Date getDate () {return  date; }
    @Override
    public String toString() {
        return Long.toString(token);
    }

}
