package com.jeseromero;

import com.jeseromero.controller.MirrorController;
import com.jeseromero.core.managers.SubtitleManager;
import com.jeseromero.core.model.Subtitle;
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

	private static MirrorController mirrorController = MirrorController.instance();

	public SSTorrent() {
		super();
	}

	/**
	 * Initialize the web application
	 */
	@PostConstruct
	public static void initialize() {
		if (!SubtitleManager.isInitialized()) {
			SubtitleManager.init();
		}

		mirrorController.checkExistenceOfAllMirrors();
	}

	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(LoginResource.class);
		classes.add(AdminResource.class);
		classes.add(RegisterResource.class);
		classes.add(UserResources.class);
		classes.add(SearchResource.class);
		classes.add(LogoutResource.class);

		return classes;
	}

}
