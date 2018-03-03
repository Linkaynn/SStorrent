package com.jeseromero.core.model;

import java.util.ArrayList;
import java.util.Collection;

public class Episode {

	private String name;

	private Collection<SubtitleGroup> subtitleGroups;

	public Episode(String name) {
		this.name = name;
		subtitleGroups = new ArrayList<>();
	}

	public void add(SubtitleGroup subtitleGroup) {
		subtitleGroups.add(subtitleGroup);
	}

	public String getName() {
		return name;
	}

	public Collection<SubtitleGroup> getSubtitlesGroups() {
		return subtitleGroups;
	}
}
