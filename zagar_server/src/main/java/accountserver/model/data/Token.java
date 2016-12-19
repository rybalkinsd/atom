package accountserver.model.data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by eugene on 10/13/16.
 */
// @SuppressWarnings("DefaultFileTemplate")
@Entity(name = "Tokens")
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(name = "token_string")
    private final String tokenString;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_create")
    private final Calendar createdAt = Calendar.getInstance();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_request")
    private Calendar lastRequestAt = createdAt;

    @Column(name = "active")
    private boolean active = true;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false, unique = true, updatable = false)
    private UserProfile user;

    private static final Random generator = new Random();

    private static String generateToken(){
        return ((Long) generator.nextLong()).toString();
    }

    public static Token valueOf(String tokenString){
        return new Token(tokenString);
    }

    private Token() {
        this.tokenString = generateToken();
    }

    private Token(String tokenString) {
        this.tokenString = tokenString;
    }

    public Token(@NotNull UserProfile user) {
        this();
        this.user = user;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(@NotNull UserProfile user) {
        this.user = user;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public Calendar getLastRequestAt() {
        return lastRequestAt;
    }

    public void setLastRequestAt(Calendar lastRequestAt) {
        this.lastRequestAt = lastRequestAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        return toString().equals(token1.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return tokenString;
    }
}
