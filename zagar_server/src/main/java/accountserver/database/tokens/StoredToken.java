package accountserver.database.tokens;

import accountserver.database.users.User;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * Created by xakep666 on 03.11.16.
 * <p>
 * Needed to store token - owner relation
 */
@Entity
@Table(name = "tokens")
class StoredToken {
    @EmbeddedId
    private Token token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    protected StoredToken() {
    }

    StoredToken(@NotNull Token token, @NotNull User owner) {
        this.token = token;
        this.owner = owner;
    }

    User getOwner() {
        return owner;
    }

    Token getToken() {
        return token;
    }

}