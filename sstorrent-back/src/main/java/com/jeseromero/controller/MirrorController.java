package com.jeseromero.controller;


import com.jeseromero.core.controller.ConfigurationController;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.model.Mirror;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.util.SLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

		Configuration mirrorConfig = configurationController.getConfigurationByName(mirror);

		if (mirrorConfig == null) {
			logger.warning(mirror + " doesn't exist like a mirror. Possible mirrors are: " + configurationController.allConfigurations().stream().map((Configuration::getName)).reduce((s, s2) -> s + ", " + s2));

			return null;
		}
		return mirrorConfig;
	}

	private String getCollapsedName(String mirror) {
		Session session = DBSessionFactory.getSession();

		Query query = session.createQuery(String.format("SELECT collapsedName FROM Mirror WHERE name = '%s'", mirror));

		return (String) query.list().get(0);
	}

	public Set<Mirror> getMirrors(String[] _mirrors) {
		Set<Mirror> mirrors = new HashSet<>();

		Set<Mirror> allMirrors = getAllMirrors();

		for (String _mirror : _mirrors) {
			for (Mirror mirror : allMirrors) {
				if (mirror.getName().equals(_mirror)) {
					mirrors.add(mirror);
				}
			}
		}

		return mirrors;
	}

	public Set<Mirror> getAllMirrors() {
		Session session = DBSessionFactory.getSession();

		Set<Mirror> mirrors = new HashSet<>();

		Transaction transaction = session.beginTransaction();
		List<Mirror> from_mirror = session.createQuery("from Mirror").list();
		transaction.commit();

		for (Mirror mirror : from_mirror) {
			mirrors.add(mirror);
		}

		return mirrors;
	}

	public Set<Mirror> getAllWorkingMirrors() {
		return getAllMirrors().stream().filter(Mirror::isWorking).collect(Collectors.toSet());
	}

	public List<String> getAllWorkingMirrorsName() {
		return getAllWorkingMirrors().stream().map(Mirror::getName).collect(Collectors.toList());
	}

	public void checkExistenceOfAllMirrors() {
		Set<Mirror> allMirrors = getAllMirrors();

		Set<Mirror> newMirrors = new HashSet<>();

		boolean exist;

		for (Configuration configuration : new ConfigurationController().allConfigurations()) {
			exist = false;

			for (Mirror mirror : allMirrors) {
				if (mirror.getName().equals(configuration.getName())) {
					exist = true;
				}

				if (exist) break;
			}

			if (!exist) {
				newMirrors.add(new Mirror(configuration.getName(), configuration.getLanguage(), configuration.isWorking()));
			}
		}

		Session session = DBSessionFactory.getSession();

		for (Mirror newMirror : newMirrors) {
			newMirror.save(session);
		}
	}
}
