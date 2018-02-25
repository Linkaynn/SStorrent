package com.jeseromero.model;

import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SModel {

	public void save(Session session) {
		Transaction transaction = session.beginTransaction();
		session.save(this);
		transaction.commit();
	}

	public void refresh() {
	    DBSessionFactory.getSession().refresh(this);
	}
}
