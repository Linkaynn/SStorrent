package com.jeseromero.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SLogger {

    private static boolean debugging = true;

    private final Logger logger;

    public SLogger(String className) {
        this.logger = Logger.getLogger(className);
    }

    public void info(String message) {
        logger.log(Level.INFO, message);
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
