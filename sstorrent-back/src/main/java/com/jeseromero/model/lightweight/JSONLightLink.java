package com.jeseromero.model.lightweight;

public class JSONLightLink extends JSONable {
	private final String linkType;
	private final String link;

	public JSONLightLink(String linkType, String link) {
		this.linkType = linkType;
		this.link = link;
	}
}
