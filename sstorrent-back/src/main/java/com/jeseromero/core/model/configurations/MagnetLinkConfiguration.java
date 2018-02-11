package com.jeseromero.core.model.configurations;

import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Linkaynn on 19/02/2017.
 * SSTorrent
 */
public class MagnetLinkConfiguration extends Configuration {

    public MagnetLinkConfiguration() {
        name = "MagnetLink";
        searchUrl = "https://magnetlink.in/search.php?f=%s&p=%s";
        rowSelector = "tbody tr";
        magnetSelector = "a[title=\"Download Magnet\"]";
	    directDownloadSelector = "";

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

		Elements rows = document.select(rowSelector);

		if (!rows.isEmpty()) {
			rows.remove(rows.size() - 1);
		}
		return rows;
	}

	@Override
	public Torrent getTorrentLink(Element row) {
		Element link = row.select("td > a").get(0);

		Torrent torrent = new Torrent();

		torrent.setName(link.text());

		torrent.setSize(row.select("td > span.s").text());

		torrent.setDate(row.select("td > span.a > span").text());

		torrent.setSeeders(row.select("td > span.u").text());

		torrent.setLeechers(row.select("td > span.d").text());

		torrent.setUrl("https://magnetlink.in" + link.attr("href"));

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
