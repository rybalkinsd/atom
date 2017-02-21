package accountserver.database.tokens;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.Random;

/**
 * Created by xakep666 on 23.10.16.
 * <p>
 * Token is a unique identifier of user
 */
@Embeddable
public class Token implements Serializable {
    private static final Duration LIFE_TIME = Duration.ofHours(2);

    @Column(name = "token_value", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long token;
    @Column(name = "token_issue_date", nullable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date generationDate = new Date();
    @Column(name = "token_valid_until", nullable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil = new Date(generationDate.getTime() + LIFE_TIME.toMillis());

    /**
     * Generates new random token
     */
    protected Token() {
        token = new Random().nextLong();
    }

    /**
     * Determine if token is valid
     *
     * @return true if valid, false otherwise
     */
    boolean isValid() {
        Date now = new Date();
        return now.after(generationDate) && now.before(validUntil);
    }

    /**
     * Compare token value with raw string
     *
     * @param rawToken string to compare
     * @return true if equals, false otherwise
     */
    boolean rawEquals(@NotNull String rawToken) {
        return Long.valueOf(token).toString().equals(rawToken);
    }

    @Override
    public boolean equals(Object o) {
        return (o == this) || (o instanceof Token) && ((Token) o).token == this.token;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(token).hashCode();
    }

    @Override
    public String toString() {
        return Long.valueOf(token).toString();
    }
}
