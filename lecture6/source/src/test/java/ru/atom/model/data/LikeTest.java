package ru.atom.model.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

public class LikeTest {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";
    private static final String URL;
    private static final String HOST = "54.224.37.210";
    private static final int PORT = 5432;
    private static final String DB_NAME = "testdb";
    private static final String USER = "rybalkin1";
    private static final String PASSWORD = "1234";

    static {
        URL = String.format(URL_TEMPLATE, HOST, PORT, DB_NAME);
    }

//    private SessionFactory newSessionFactory() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        //log settings
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.show_sql", "true");
//        //driver settings
//        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
//        properties.put("hibernate.connection.url", URL);
//        properties.put("hibernate.connection.username", USER);
//        properties.put("hibernate.connection.password", PASSWORD);
//        properties.put("connection_pool_size", 1);
//
//        return new Configuration()
//                .addProperties(properties)
//                .addAnnotatedClass(Like.class)
//                .buildSessionFactory(
//                        new StandardServiceRegistryBuilder()
//                                .applySettings(properties)
//                                .build()
//                );
//    }

    @Test
    public void testHibernate() throws Exception {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();

        Transaction txn = null;
        try (Session session = sessionFactory.openSession()) {
            txn = session.beginTransaction();
            List<Like> likes = session.createQuery("from Like").list();
            System.out.println(likes);
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

}