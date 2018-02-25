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
public class EliteTorrentConfiguration extends Configuration {

    public EliteTorrentConfiguration() {
        name = "Mirror 4";
        language = Languages.Spanish;
        searchUrl = "https://www.elitetorrent.biz/page/%s/?s=%s&x=0&y=0";
        rowSelector = ".imagen";
        magnetSelector = ".enlace_torrent";
	    directDownloadSelector = magnetSelector;
	    working = true;
    }

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String buildSearchUrl(String text, Integer index) {
		return String.format(searchUrl, index, text);
	}

	@Override
	public Elements getRows(Document document) {
		return document.select(rowSelector);
	}

	@Override
	public Torrent getTorrentLink(Element row) {
		Element link = row.select("a").get(0);

		Torrent torrent = new Torrent();

		torrent.setName(link.attr("alt"));

		torrent.setSize(row.select(".voto1 > .dig1").get(0).text() + row.select(".voto1 > .dig2").get(0).text());

		torrent.setDate("na");

		torrent.setSeeders("na");

		torrent.setLeechers("na");

		torrent.setUrl(link.attr("href"));

		return torrent;
	}

	@Override
	public String getMagnetLink(Document document) {
		return document.select(magnetSelector).get(1).attr("href");
	}

	@Override
	public String getDirectDownloadLink(Document document) {
		return document.select(directDownloadSelector).get(0).attr("href");
	}
}
