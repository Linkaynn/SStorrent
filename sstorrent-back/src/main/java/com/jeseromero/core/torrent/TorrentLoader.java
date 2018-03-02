package com.jeseromero.core.torrent;

import com.jeseromero.core.controller.JSOUPController;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TorrentLoader {

	private JSOUPController jsoupController;

	private final Configuration configuration;
	private final String textToSearch;
	private final int index;

	public TorrentLoader(Configuration configuration, String textToSearch, int index) {
		this.configuration = configuration;
		this.textToSearch = textToSearch;
		this.index = index;

		jsoupController = new JSOUPController();
	}

	public Collection<Torrent> retrieveTorrents() {
		List<Torrent> torrents = new ArrayList<>();

		Document document = getDocument();

		if (document != null) {
			Elements rows = configuration.getRows(document);

			for (Element row : rows) {
				Torrent torrent = configuration.getTorrentLink(row);

				torrents.add(torrent);
			}

		}

		return torrents;
	}

	private Document getDocument() {
		return jsoupController.getHtmlDocument(configuration.buildSearchUrl(textToSearch, index));
	}
}
