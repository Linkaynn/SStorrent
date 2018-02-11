package com.jeseromero.controller;


import com.jeseromero.core.controller.ConfigurationController;
import com.jeseromero.core.controller.TorrentSearcher;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collection;

public class SearchController {

    private static SearchController instance;

    public static SearchController instance() {

        if (instance == null) {
            instance = new SearchController();
        }

        return instance;
    }

    public Collection<Torrent> search(String mirror, String value, int index) {
        ConfigurationController configurationController = new ConfigurationController();

        Configuration mirrorConfig = configurationController.getConfigurationByName(getCollapsedName(mirror));

        if (mirrorConfig == null) {
            throw new IllegalArgumentException(mirror + " doesn't exist like a mirror. Possible mirrors are: " + configurationController.allConfigurations().stream().map((Configuration::getName)).reduce((s, s2) -> s + ", " + s2));
        }

        return new TorrentSearcher(mirrorConfig).search(value, index).get(mirrorConfig);
    }

    private String getCollapsedName(String mirror) {
        Session session = DBSessionFactory.instance().openSession();

        try {
            Query query = session.createQuery(String.format("SELECT collapsedName FROM Mirror WHERE name = '%s'", mirror));

            return (String) query.list().get(0);
        } finally {
            session.close();
        }
    }
}
