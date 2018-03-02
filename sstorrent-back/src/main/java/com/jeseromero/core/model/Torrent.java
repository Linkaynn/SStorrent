package com.jeseromero.core.model;

public class Torrent {

	private String name;

    private String url;

	private String date;

	private String size;

	private String seeders;

	private String leechers;

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getUrl() {
        return url;
    }

	public void setUrl(String url) {
		this.url = url;
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
