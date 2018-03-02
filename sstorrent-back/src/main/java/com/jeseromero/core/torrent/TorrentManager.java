package com.jeseromero.core.torrent;


import com.jeseromero.core.controller.JSOUPController;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.lightweight.JSONLightLink;
import com.jeseromero.util.SLogger;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collection;

public class TorrentManager {

	private static SLogger logger = new SLogger(TorrentManager.class.getName());

    private Configuration configuration;

	private int retries = 3;

	public TorrentManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public Collection<Torrent> search(String text, int index) {

	    Collection<Torrent> torrents = new ArrayList<>();

	    TorrentLoader torrentLoader = new TorrentLoader(configuration, text, index);

	    try {
	        torrents = torrentLoader.retrieveTorrents();
	    } catch (Exception e) {
	        logger.warning("Failed searching for " + text);

	        if (retries > 0) {
		        logger.warning("Retrying search of " + text);
		        retries--;
		        search(text, index);
	        } else {
	        	logger.warning("Retries for " + text + " exceeded...");
	        	logger.error(e);
	        }
	    }

	    return torrents;
    }

    public JSONLightLink retrieveLink(String torrentLink) {
        Document htmlDocument = new JSOUPController().getHtmlDocument(torrentLink);

        if (htmlDocument != null) {
            String magnetLink = configuration.getMagnetLink(htmlDocument);

            if (magnetLink != null && !magnetLink.isEmpty()) {
            	return new JSONLightLink("magnet", magnetLink);
            }

            String directDownloadLink = configuration.getDirectDownloadLink(htmlDocument);

	        if (directDownloadLink != null && !directDownloadLink.isEmpty()) {
		        return new JSONLightLink("direct", magnetLink);
	        }
        }

        return null;
    }
}
