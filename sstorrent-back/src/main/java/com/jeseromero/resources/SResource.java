package com.jeseromero.resources;

import com.jeseromero.controller.SessionController;
import com.jeseromero.model.User;
import com.jeseromero.util.SLogger;

public class SResource {

	protected static final SLogger logger = new SLogger(SResource.class.getName());

	private final SessionController sessionController = SessionController.instance();

	protected User getUser(String token) {
		return sessionController.getUser(token);
	}

}
