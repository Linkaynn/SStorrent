package com.jeseromero.controller;


import com.jeseromero.core.controller.ConfigurationController;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.model.Mirror;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.util.SLogger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MirrorController {

	private static final SLogger logger = new SLogger(MirrorController.class.getName());

    private static MirrorController instance;

    public static MirrorController instance() {
        if (instance == null) {
            instance = new MirrorController();
        }

        return instance;
    }

	public Configuration getConfiguration(String mirror) {
		ConfigurationController configurationController = new ConfigurationController();

		Configuration mirrorConfig = configurationController.getConfigurationByName(getCollapsedName(mirror));

		if (mirrorConfig == null) {
			logger.warning(mirror + " doesn't exist like a mirror. Possible mirrors are: " + configurationController.allConfigurations().stream().map((Configuration::getName)).reduce((s, s2) -> s + ", " + s2));

			return null;
		}
		return mirrorConfig;
	}

	private String getCollapsedName(String mirror) {
		Session session = DBSessionFactory.openSession();

		Query query = session.createQuery(String.format("SELECT collapsedName FROM Mirror WHERE name = '%s'", mirror));

		return (String) query.list().get(0);
	}

	public Set<Mirror> getMirrors(String[] _mirrors) {
		Set<Mirror> mirrors = new HashSet<>();

		Session session = DBSessionFactory.openSession();

		List<Mirror> allMirrors = session.createQuery("from Mirror").list();

		for (String _mirror : _mirrors) {
			String collapsedName = getCollapsedName(_mirror);

			for (Mirror mirror : allMirrors) {
				if (mirror.getCollapsedName().equals(collapsedName)) {
					mirrors.add(mirror);
				}
			}
		}

		return mirrors;
	}

	public Set<Mirror> getAllMirrors() {
		Session session = DBSessionFactory.openSession();

		Set<Mirror> mirrors = new HashSet<>();

		for (Mirror mirror : ((List<Mirror>) session.createQuery("from Mirror").list())) {
			mirrors.add(mirror);
		}

		return mirrors;
	}
}
