package com.jeseromero.core.controller;


import com.jeseromero.core.controller.runnable.TorrentLoader;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.lightweight.JSONLightLink;
import org.jsoup.nodes.Document;

import javax.ejb.AsyncResult;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TorrentSearcher {

    private Configuration configuration;

	private Collection<Torrent> torrents;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

	public TorrentSearcher(Configuration configuration) {
        this.configuration = configuration;
    }

    public Collection<Torrent> search(String text, int index) {

	    TorrentLoader torrentLoader = new TorrentLoader(configuration, text, index);

	    try {
	        torrents = executorService.submit(torrentLoader).get();
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return torrents;
    }

    public JSONLightLink retrieveLink(String link) {
        Document htmlDocument = new JSOUPController().getHtmlDocument(link);

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
