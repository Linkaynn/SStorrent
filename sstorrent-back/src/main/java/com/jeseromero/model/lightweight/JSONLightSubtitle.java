package com.jeseromero.model.lightweight;

import com.jeseromero.core.model.Subtitle;

public class JSONLightSubtitle extends JSONable {

	private final String language;

	private final String link;

	public JSONLightSubtitle(Subtitle subtitle) {
		this.language = subtitle.getLanguage();
		this.link = subtitle.getLink();
	}
}
