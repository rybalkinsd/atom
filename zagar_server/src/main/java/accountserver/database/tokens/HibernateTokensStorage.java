package accountserver.database.tokens;

import accountserver.database.users.HibernateUsersStorage;
import accountserver.database.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.HibernateHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by xakep666 on 03.11.16.
 * <p>
 * Stores {@link Token} in database and validates it
 */
public class HibernateTokensStorage implements TokenDao {
    private static final Logger log = LogManager.getLogger(HibernateUsersStorage.class);
    private Thread prThread;

    public HibernateTokensStorage() {
        prThread = new Thread(this::periodicRemover);
        log.info("Initialized Hibernate tokens storage");
        prThread.start();
    }

    @Override
    public @NotNull Token generateToken(@NotNull User user) {
        Token foundToken = getUserToken(user);
        if (foundToken != null) {
            log.info("Found token for user " + user + " , returning");
            return foundToken;
        }
        log.info("Valid tokens for user " + user + " not found, creating new");
        StoredToken token = new StoredToken(new Token(), user);
        HibernateHelper.doTransactional(session -> session.save(token));
        return token.getToken();
    }

    @Override
    public @Nullable Token getUserToken(@NotNull User user) {
        log.info("Searching token for user " + user);
        List response = HibernateHelper.selectTransactional(session ->
                session.createQuery("from StoredToken t where t.owner.id = :id and " +
                        "t.token.validUntil >= :ts and t.token.generationDate <= :ts")
                        .setParameter("ts", new Date())
                        .setParameter("id", user.getId())
                        .list());
        if (response == null) {
            log.error("Error retrieving token for user " + user);
            return null;
        }
        if (response.size() == 0) {
            log.info("Valid tokens for user " + user + " not found");
            return null;
        }
        return ((StoredToken) response.get(0)).getToken();
    }

    @Override
    public @Nullable User getTokenOwner(@NotNull Token token) {
        log.info("Searching token " + token + " owner");
        List response = HibernateHelper.selectTransactional(session ->
                session.createQuery("from StoredToken t where t.token = :val and " +
                        "t.token.validUntil >= :ts and t.token.generationDate <= :ts")
                        .setParameter("ts", new Date())
                        .setParameter("val", token)
                        .list());
        if (response == null) {
            log.error("Error searching token " + token + " owner");
            return null;
        }
        if (response.size() == 0) {
            log.info("Token " + token + " owner not found");
            return null;
        }
        return ((StoredToken) response.get(0)).getOwner();
    }

    @Nullable
    @Contract("null -> null")
    @Override
    public Token findByValue(@Nullable String rawToken) {
        long token;
        try {
            token = Long.parseLong(rawToken);
        } catch (NumberFormatException e) {
            return null;
        }
        log.info("Searching token by value" + rawToken);
        List response = HibernateHelper.selectTransactional(session ->
                session.createQuery("from StoredToken t where t.token.token = :val and " +
                        "t.token.validUntil >= :ts and t.token.generationDate <= :ts")
                        .setParameter("ts", new Date())
                        .setParameter("val", token)
                        .list());
        if (response == null) {
            log.error("Error searching token by value " + rawToken);
            return null;
        }
        if (response.size() == 0) {
            log.info("Token " + rawToken + " not found");
            return null;
        }

        return ((StoredToken) response.get(0)).getToken();
    }

    @Override
    public @NotNull List<User> getValidTokenOwners() {
        List resp = HibernateHelper.selectTransactional(session ->
                session.createQuery("from StoredToken t where " +
                        "t.token.validUntil >= :ts and t.token.generationDate <= :ts")
                        .setParameter("ts", new Date())
                        .list());
        if (resp == null) {
            log.error("Error returning valid token owners");
            return Collections.emptyList();
        }

        List<User> ret = new ArrayList<>(resp.size());
        resp.forEach(st -> ret.add(((StoredToken) st).getOwner()));
        return ret;
    }

    @Override
    public void removeToken(@NotNull Token token) {
        HibernateHelper.doTransactional(session ->
                session.createQuery("delete from StoredToken t where t.token = :id")
                        .setParameter("id", token)
                        .executeUpdate());
    }

    @Override
    public void removeToken(@NotNull User user) {
        HibernateHelper.doTransactional(session ->
                session.createQuery("delete from StoredToken t where t.owner.id = :id")
                        .setParameter("id", user.getId())
                        .executeUpdate());
    }

    private void periodicRemover() {
        log.info("Periodic removing of invalid tokens activated");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                log.info("Time to remove tokens");
                HibernateHelper.doTransactional(session -> session.createQuery("delete from StoredToken t where " +
                        "t.token.validUntil <= :ts or t.token.generationDate >= :ts")
                        .setParameter("ts", new Date())
                        .executeUpdate());
                Thread.sleep(TOKEN_REMOVAL_INTERVAL.toMillis());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        prThread.interrupt();
    }
}
