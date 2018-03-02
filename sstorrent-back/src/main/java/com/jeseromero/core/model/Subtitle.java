package com.jeseromero.core.model;

public class Subtitle {

	private String language;

	private String link;

	public Subtitle(String language, String link) {
		this.language = language;
		this.link = link;
	}

	public String getLanguage() {
		return language;
	}

	public String getLink() {
		return link;
	}
}
