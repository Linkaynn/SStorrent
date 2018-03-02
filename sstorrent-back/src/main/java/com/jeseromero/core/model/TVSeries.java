package com.jeseromero.core.model;

public class TVSeries {

	private String name;

	private String id;

	private int seasons;

	public TVSeries(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	public int getSeasons() {
		return seasons;
	}
}
