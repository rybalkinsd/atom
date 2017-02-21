package accountserver.dao;

import accountserver.dao.exceptions.DaoException;
import accountserver.database.SessionHolder;
import accountserver.database.TransactionHolder;
import accountserver.model.data.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by eugene on 10/10/16.
 */

public class UserProfileHibernate
        implements UserDAO {
    @Override
    public void remove(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(UserProfile in) throws DaoException {
        throw new NotImplementedException();
    }

    private static final String ENTITY_NAME = "Profiles";
    private static final String ALIAS = "user";
    private static final Logger LOG = LogManager.getLogger(UserProfileHibernate.class);

    private final SessionHolder holder = SessionHolder.getHolder();

    private org.hibernate.query.Query<UserProfile> getWhereQuery(String ...conditions){
        StringJoiner query = new StringJoiner(" and ", String.format("from %s as %s where ", ENTITY_NAME, ALIAS) ,"");
        for (String condition : conditions){
            query.add(condition);
        }

        return holder.getSession().createQuery(query.toString(), UserProfile.class);
    }

    @Override
    public Long insert(UserProfile in) throws DaoException {
        try {
            return (Long) TransactionHolder.getTransactionHolder().getSession().save(in);
        }
        catch (RuntimeException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public UserProfile getById(Long id) {
        return holder.getSession().get(UserProfile.class, id);
    }

    @Override
    public List<UserProfile> getWhere(String... conditions) {
        return getWhereQuery(conditions).list();
    }

    @Override
    public List<UserProfile> getAll() throws DaoException {
        throw new NotImplementedException();
    }

    @Override
    public UserProfile getByEmail(String email) {
        return holder.getSession().byNaturalId(UserProfile.class).using("email",email).loadOptional().orElse(null);
    }

    public void update(Long id, String field, String value) throws DaoException {
        try (TransactionHolder holder = TransactionHolder.getTransactionHolder()) {
            holder.getSession().createQuery(String.format("update versioned %s set %s = :value where id = :id", ENTITY_NAME, field)).
                    setParameter("value",value).
                    setParameter("id",id).
                    executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new DaoException(e);
        }
    }

}
