package com.jeseromero.core.model;

import java.util.ArrayList;
import java.util.Collection;

public class SubtitleGroup {

	private String type;

	private Collection<Subtitle> subtitles;

	public SubtitleGroup(String type) {
		this.type = type;
		subtitles = new ArrayList<>();
	}

	public void add(Subtitle subtitle) {
		subtitles.add(subtitle);
	}

	public String getType() {
		return type;
	}

	public Collection<Subtitle> getSubtitles() {
		return subtitles;
	}
}
