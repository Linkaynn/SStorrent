package com.jeseromero.persistence;

import com.jeseromero.util.SLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBSessionFactory {

    private static final SLogger logger = new SLogger(DBSessionFactory.class.getName());

    private static SessionFactory sessionFactory;

	static {
		createSessionFactory();
	}

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

            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.username", System.getenv("DATABASE_USER"));
            configuration.setProperty("hibernate.connection.password", System.getenv("DATABASE_PASSWORD"));

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            logger.error(e);
        }
    }
}