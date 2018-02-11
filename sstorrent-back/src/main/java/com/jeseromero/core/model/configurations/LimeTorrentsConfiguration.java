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
public class LimeTorrentsConfiguration extends Configuration {

    public LimeTorrentsConfiguration() {
        name = "LimeTorrents";
        searchUrl = "https://www.limetorrents.cc/search/all/%s/date/%s/";
        rowSelector = ".table2 tbody tr";
        magnetSelector = "div.downloadarea div.dltorrent p a";
	    directDownloadSelector = magnetSelector;
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
			rows.remove(0);
		}

		return rows;
	}

	@Override
	public Torrent getTorrentLink(Element row) {
		Element link = row.select("td > div > a").get(1);

		Torrent torrent = new Torrent();

		torrent.setName(link.text());

		torrent.setSize(row.select("td.tdnormal").get(1).text());

		torrent.setDate(row.select("td.tdnormal").get(0).text().split("-")[0].trim());

		torrent.setSeeders(row.select("td.tdseed").text());

		torrent.setLeechers(row.select("td.tdleech").text());

		torrent.setUrl("https://www.limetorrents.cc" + link.attr("href"));

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
