package com.jeseromero.core.model.configurations;

import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Languages;
import com.jeseromero.core.model.Torrent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Linkaynn on 19/02/2017.
 * SSTorrent
 */
public class PirateBayConfiguration extends Configuration {

    public PirateBayConfiguration() {
        name = "Mirror 3";
	    language = Languages.English;
        searchUrl = "https://pirateproxy.tf/search/%s/%s/7//";
        rowSelector = "#searchResult tr";
        magnetSelector = "a[title=\"Get this torrent\"]";
	    directDownloadSelector = "";
	    working = true;
    }

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String buildSearchUrl(String text, Integer index) {
		return String.format(searchUrl, text, index);
	}

	@Override
	public Elements getRows(Document document) {
		Elements elements = document.select(rowSelector);
		if (!elements.isEmpty()) {
			elements.remove(0);
		}
		return elements;

	}

	@Override
	public Torrent getTorrentLink(Element row) {

		Torrent torrent = new Torrent();

		Elements link = row.select(".detLink");

		torrent.setName(link.text());

		String[] data = row.select(".detDesc").text().split(" ");

		torrent.setDate(data[1].substring(0, data[1].length() - 1));

		torrent.setSize(data[3].substring(0, data[3].length() - 1));

		torrent.setSeeders(row.select("td").get(2).text());

		torrent.setLeechers(row.select("td").get(3).text());

		torrent.setUrl("https://pirateproxy.tf" + link.attr("href"));
		return torrent;
	}

	@Override
	public String getMagnetLink(Document document) {
		return document.select(magnetSelector).get(0).attr("href");
	}

	@Override
	public String getDirectDownloadLink(Document document) {
		return null;
	}
}
