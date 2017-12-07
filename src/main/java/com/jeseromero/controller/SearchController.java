package com.jeseromero.controller;


import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.sstorrent.core.controller.ConfigurationController;
import com.jeseromero.sstorrent.core.controller.TorrentSearcher;
import com.jeseromero.sstorrent.core.model.Configuration;
import com.jeseromero.sstorrent.core.model.Torrent;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collection;

public class SearchController {
    public Collection<Torrent> search(String mirror, String value) {
        ConfigurationController configurationController = new ConfigurationController();

        Configuration mirrorConfig = configurationController.getConfigurationByName(getCollapsedName(mirror));

        if (mirrorConfig == null) {
            throw new IllegalArgumentException(mirror + " doesn't exist like a mirror. Posible mirrors are: " + configurationController.allConfigurations().stream().map((Configuration::getName)).reduce((s, s2) -> s + ", " + s2));
        }

        Collection<Torrent> torrents = new TorrentSearcher(mirrorConfig).search(value).get(mirrorConfig);

        return torrents;
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
