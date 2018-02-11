package com.jeseromero.core.controller.runnable;

import com.jeseromero.core.controller.JSOUPController;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Created by Linkaynn on 20/02/2017.
 * SSTorrent
 */
public class TorrentLoader implements Callable<Collection<Torrent>> {

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

	@Override
	public Collection<Torrent> call() {
		List<Torrent> torrents = new ArrayList<>();

		Collection<Thread> threads = new ArrayList<>();

		Document document = getDocument();

		if (document != null) {
			Elements rows = configuration.getRows(document);

			for (Element row : rows) {
				Torrent torrent = configuration.getTorrentLink(row);

				torrent.setConfiguration(configuration);
//
//				Thread thread = new Thread(new TorrentFiller(torrent));
//				thread.start();
//				threads.add(thread);

				torrents.add(torrent);
			}

			waitFor(threads);

			torrents = torrents.stream().filter(torrent -> torrent.getDirectDownloadLink() == null).collect(Collectors.toList());
		}

		return torrents;
	}

	private void waitFor(Collection<Thread> threads) {
		for (Thread thread : threads) {
			try {
				thread.join(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Document getDocument() {
		return jsoupController.getHtmlDocument(configuration.buildSearchUrl(textToSearch, index));
	}

}
