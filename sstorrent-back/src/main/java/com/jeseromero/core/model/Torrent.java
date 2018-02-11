package com.jeseromero.core.model;

import com.jeseromero.core.controller.JSOUPController;
import org.jsoup.nodes.Document;

/**
 * Created by Linkaynn on 12/02/2017.
 * TorrentSearcher
 */
public class Torrent {

	private String name;

    private String url;

	private String date;

	private String size;

	private String seeders;

	private String leechers;

	private String magnetLink;

    private String directDownloadLink;

	private Configuration configuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMagnetLink() {
        return magnetLink;
    }

	public String getDirectDownloadLink() {
		return directDownloadLink;
	}

	public String getUrl() {
        return url;
    }

	public void setUrl(String url) {
		this.url = url;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public String getSize() {
		return size;
	}

	public void setSeeders(String seeders) {
		this.seeders = seeders;
	}

	public void setLeechers(String leechers) {
		this.leechers = leechers;
	}

	public String getSeeders() {
		return seeders;
	}

	public String getLeechers() {
		return leechers;
	}
}
