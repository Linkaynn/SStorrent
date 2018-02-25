package com.jeseromero.controller;

import com.jeseromero.model.Profile;
import com.jeseromero.model.Search;
import com.jeseromero.model.User;
import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.stream.Collectors;

public class UserController {

	private static UserController instance = null;

	private SessionController sessionController = SessionController.instance();

	private MirrorController mirrorController = MirrorController.instance();

    public static UserController instance() {
        if (instance == null) {
            instance = new UserController();
        }

        return instance;
    }

    public boolean exist(int userID) {
        Session session = DBSessionFactory.getSession();

	    return session.get(User.class, userID) != null;
    }

    public Profile getProfile(User user) {
        Profile profile = new Profile();

	    profile.setSearches(user.getSearches().stream().map(Search::getSearch).collect(Collectors.toSet()));

	    return profile;
    }

	public void updateProfile(User user, String name, String[] mirrors) {
		user.setName(name);

		user.setMirrors(mirrorController.getMirrors(mirrors));

		Session session = DBSessionFactory.getSession();

		Transaction transaction = session.beginTransaction();
		session.merge(user);
		transaction.commit();
	}

	public void changePassword(User user, String newPassword) {
		user.setPassword(newPassword);

		Session currentSession = DBSessionFactory.getSession();

		Transaction transaction = currentSession.beginTransaction();
		currentSession.merge(user);
		transaction.commit();
	}

}
