package com.jeseromero.persistence;

import com.jeseromero.util.SLogger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBSessionFactory {

    private static final SLogger logger = new SLogger(DBSessionFactory.class.getName());

    private static SessionFactory sessionFactory;

    public static SessionFactory instance() {
        if (sessionFactory == null) {
            createSession();
        }

        return sessionFactory;
    }

    private static void createSession() {
        try {

            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        } catch (Exception e) {
            logger.error(e);
        }
    }
}