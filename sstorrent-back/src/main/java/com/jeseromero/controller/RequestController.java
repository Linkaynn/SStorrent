package com.jeseromero.controller;


import com.jeseromero.model.Request;
import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RequestController {

    private static RequestController instance;

    public static RequestController instance() {
        if (instance == null) {
            instance = new RequestController();
        }

        return instance;
    }

	public void deleteRequestFrom(String username) {
		Session session = DBSessionFactory.openSession();

		try {
			List<Request> requests = session.createQuery("from Request where username = '" + username + "' ").list();

			Transaction transaction = session.beginTransaction();
			for (Request request : requests) {
				session.delete(request);
			}
			transaction.commit();
		} catch (Exception ignored) {
			// No requests found
		}
	}
}
