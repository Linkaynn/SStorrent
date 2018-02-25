package com.jeseromero.util;

import com.jeseromero.model.Log;
import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SLogger {

    private static boolean debugging = true;

    private final Logger logger;

    public SLogger(String className) {
        this.logger = Logger.getLogger(className);
    }

	public void info(String username, String message, Object... args) {
    	message = username + ":\t " + String.format(message, args);

    	logger.log(Level.INFO, message);

		this.registerLog(username, String.format(message, args));
    }

    private void registerLog(String username, String message){
	    Session session = DBSessionFactory.openSession();

	    Transaction transaction = session.beginTransaction();

	    session.save(new Log(username, message, new Date()));

	    transaction.commit();

    }

    public void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    public void debug(String message) {
        if (debugging) {
            logger.log(Level.INFO, message);
        }
    }

    public void error(Throwable e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }
}
