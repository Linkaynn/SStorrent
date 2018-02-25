package com.jeseromero.controller;


import com.jeseromero.model.Token;
import com.jeseromero.model.User;
import com.jeseromero.model.lightweight.JSONLightUser;
import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class SessionController {

	private static RequestController requestController = RequestController.instance();

    private static SessionController instance;

    public static SessionController instance() {
        if (instance == null) {
            instance = new SessionController();
        }

        return instance;
    }

	public JSONLightUser login(String username, String password) {

		Session session = DBSessionFactory.openSession();

		try {

			Query<User> query = session.createQuery("FROM User WHERE username='" + username + "' AND password='" + password + "'", User.class);

			User user = query.uniqueResult();

			Token token = new Token();

			user.setToken(token.getToken());

			user.save(session);

			requestController.deleteRequestFrom(username);

			return new JSONLightUser(user, token);

		} catch (Exception e) {
			return null;
		}
	}

	public void logout(User user) {
		user.setToken(null);
		user.save(DBSessionFactory.openSession());
	}

	/**
	 * Return null if not exist
	 * @param token
	 * @return
	 */
	public User getUser(String token) {
		Session session = DBSessionFactory.openSession();

		try {

			Query<User> query = session.createQuery("FROM User WHERE token ='" + token + "'", User.class);

			return query.uniqueResult();

		} catch (Exception e) {
			return null;
		}
	}
}
