package ru.atom.dbhackaton.server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class Database {
    private static final Logger log = LogManager.getLogger(Database.class);
    /**
     * SessionFactory abstracts
     */
    private static SessionFactory sessionFactory;

    public static Session session() {
        return sessionFactory.openSession();
    }

    private Database() {
    }

    /**
     * This is preferred way to initialize SessionFactory
     */
    public static void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }

}