package com.jeseromero.core.managers;


import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.core.requesters.TorrentRequester;
import com.jeseromero.model.lightweight.JSONLightLink;
import com.jeseromero.util.SLogger;

import java.util.ArrayList;
import java.util.Collection;

public class TorrentManager {

	private static SLogger logger = new SLogger(TorrentManager.class.getName());

	private int retries = 3;

	private TorrentRequester torrentRequester;

	public TorrentManager(Configuration configuration) {
		torrentRequester = new TorrentRequester(configuration);
    }

    public Collection<Torrent> search(String text, int index) {

	    Collection<Torrent> torrents = new ArrayList<>();

	    try {
	        torrents = torrentRequester.requestTorrents(text, index);
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
		return torrentRequester.requestLinkOf(torrentLink);
    }
}
