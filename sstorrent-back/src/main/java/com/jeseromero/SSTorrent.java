package com.jeseromero;

import com.jeseromero.core.controller.ConfigurationController;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.resources.*;
import com.jeseromero.util.SLogger;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class SSTorrent extends Application {

	private static final SLogger logger = new SLogger(SSTorrent.class.getName());

	public SSTorrent() {
		super();
	}

	/**
	 * Initialize the web application
	 */
	@PostConstruct
	public static void initialize() {
		for (Configuration configuration : new ConfigurationController().allConfigurations()) {

		}
	}

	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(LoginResource.class);
		classes.add(LogResource.class);
		classes.add(RegisterResource.class);
		classes.add(UserResources.class);
		classes.add(SearchResource.class);
		classes.add(LogoutResource.class);

		return classes;
	}

}
