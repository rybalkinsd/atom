package accountserver.dao;

import accountserver.dao.exceptions.DaoException;
import accountserver.database.SessionHolder;
import accountserver.database.TransactionHolder;
import accountserver.model.data.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by ivan on 06.11.16.
 */
public class TokenHibernate
        implements TokenDAO {
    private static final String ENTITY_NAME = "Tokens";
    private static final String ALIAS = "token";

    private final SessionHolder holder = SessionHolder.getHolder();

    private org.hibernate.query.Query<Token> getWhereQuery(String ...conditions) {
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", ENTITY_NAME, ALIAS), "");
        for (String condition : conditions) {
            query.add(condition);
        }


        return holder.getSession().createQuery(query.toString(), Token.class);
    }

    @Override
    public Long insert(Token in) throws DaoException {
        try {
            return (Long) TransactionHolder.getTransactionHolder().getSession().save(in);
        } catch (RuntimeException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Token getById(Long id) {
        return holder.getSession().get(Token.class, id);
    }

    @Override
    public List<Token> getWhere(String... conditions) {
        return getWhereQuery(conditions).list();
    }

    @Override
    public Token getTokenByTokenString(String tokenString) {
        return holder.getSession()
                .byNaturalId(Token.class)
                .using("tokenString", tokenString)
                .loadOptional()
                .orElse(null);
    }

    @Override
    public void removeByTokenString(String tokenString) throws DaoException {
        try (TransactionHolder holder = TransactionHolder.getTransactionHolder()) {
            holder.getSession().createQuery(String.format("delete %s where tokenString = :tokenString", ENTITY_NAME))
                    .setParameter("tokenString",tokenString)
                    .executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Token> getAll() {
        return holder.getSession().createQuery("from Tokens", Token.class).list();
    }

    @Override
    public void remove(Token token) throws DaoException {
        try (TransactionHolder holder = TransactionHolder.getTransactionHolder()) {
            holder.getSession().remove(token);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void remove(Long id) throws DaoException {
        throw new NotImplementedException();
    }



}
