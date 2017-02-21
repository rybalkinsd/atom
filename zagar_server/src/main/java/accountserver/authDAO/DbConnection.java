package accountserver.authDAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Collections;
import java.util.List;


/**
 * Created by User on 05.11.2016.
 */
public class DbConnection {
    private static final Logger log = LogManager.getLogger(DbConnection.class);
    private static final SessionFactory sessionFactory;

    private DbConnection(){}

    static {
        sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        log.info("Session factory configured.");
    }

    public static <T> List<T> selectTransaction(String query){
        Transaction transaction = null;
        List<T> result = Collections.emptyList();
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = session.createQuery(query).list();
            transaction.commit();
        }catch (Exception e){
            log.error("Transaction failed.", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            return result;
        }
    }

    public static <T> boolean insertTransaction(T t){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
            return true;
        }catch (Exception e){
            log.error("Transaction failed.", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    public static <T> boolean deleteTransaction(T t){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            return true;
        }catch (Exception e){
            log.error("Transaction failed.", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    public static <T> boolean updateTransaction(T t){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            return true;
        }catch (Exception e){
            log.error("Transaction failed.", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

}
