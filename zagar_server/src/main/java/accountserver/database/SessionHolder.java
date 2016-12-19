package accountserver.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 11/19/16.
 */
public class SessionHolder implements AutoCloseable{
    private static final Map<Object, SessionHolder> pool = new ConcurrentHashMap<>();

    public static Map<Object, SessionHolder> getPool() {
        return pool;
    }

    public static void renew() {
        SessionHolder holder = pool.get(Thread.currentThread());
        if (holder == null) new SessionHolder();
        else renew(holder);
    }

    private static void renew(SessionHolder holder) {
        if(holder.session.isOpen()) holder.session.close();
        holder.session = DbHibernate.newSession();
        LOG.debug("Session renewed");
    }

    public static SessionHolder getHolder(){
        SessionHolder holder = pool.get(Thread.currentThread());
        if (holder == null) {
            holder = new SessionHolder();
            LOG.debug("New session spawned");
            pool.put(Thread.currentThread(), holder);
            LOG.info(String.format("SessionPool{size=%d}", pool.size()));
        }
        else if (!holder.getSession().isOpen()) renew(holder);
        return holder;
    }

    private Session session;
    static final Logger LOG = LogManager.getLogger(SessionHolder.class);

    SessionHolder() {
        this(DbHibernate.newSession());
    }

    SessionHolder(Session session) {
        this.session = session;
    }

    SessionHolder(SessionHolder holder){
        this(holder.getSession());
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void close() throws DbException {
        try {
            this.session.close();
        }
        catch (RuntimeException e) {
            throw new DbException(e);
        }
    }
}
