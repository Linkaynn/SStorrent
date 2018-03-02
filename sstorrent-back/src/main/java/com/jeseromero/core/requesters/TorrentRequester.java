package com.jeseromero.core.requesters;

import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.lightweight.JSONLightLink;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TorrentRequester {

	private HTTPRequest HTTPRequest;

	private final Configuration configuration;

	public TorrentRequester(Configuration configuration) {
		this.configuration = configuration;

		HTTPRequest = new HTTPRequest();
	}

	public Collection<Torrent> requestTorrents(String textToSearch, int index) {
		List<Torrent> torrents = new ArrayList<>();

		Document document = HTTPRequest.getHtmlDocument(configuration.buildSearchUrl(textToSearch, index));

		if (document != null) {
			Elements rows = configuration.getRows(document);

			for (Element row : rows) {
				Torrent torrent = configuration.getTorrentLink(row);

				torrents.add(torrent);
			}

		}

		return torrents;
	}

	public JSONLightLink requestLinkOf(String torrentLink) {
		Document htmlDocument = new HTTPRequest().getHtmlDocument(torrentLink);

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
