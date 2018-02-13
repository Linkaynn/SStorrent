package com.jeseromero.persistence;

import com.jeseromero.util.SLogger;
import org.hibernate.Session;

public class SessionCloser implements Runnable{

	private static final SLogger logger = new SLogger(SessionCloser.class.getName());

	private Session session;

	public SessionCloser(Session session) {
		this.session = session;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(10000);

			session.close();
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}
