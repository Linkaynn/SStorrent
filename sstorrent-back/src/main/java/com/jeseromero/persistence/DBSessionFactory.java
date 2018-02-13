package com.jeseromero.persistence;

import com.jeseromero.util.SLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class DBSessionFactory {

    private static final SLogger logger = new SLogger(DBSessionFactory.class.getName());

    private static SessionFactory sessionFactory;

    public static Session openSession() {
        if (sessionFactory == null) {
            createSessionFactory();
        }

        Session newSession = sessionFactory.openSession();

        new Thread(new SessionCloser(newSession)).start();

        return newSession;
    }

    private static void createSessionFactory() {
        try {

            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        } catch (Exception e) {
            logger.error(e);
        }
    }
}