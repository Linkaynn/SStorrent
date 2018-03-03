package com.jeseromero.core.model;

import java.util.ArrayList;
import java.util.Collection;

public class Season {

	private int seasonIndex;

	private Collection<Episode> episodes;

	public Season(int seasonIndex) {
		this.seasonIndex = seasonIndex;
		episodes = new ArrayList<>();
	}

	public void add(Episode episode) {
		episodes.add(episode);
	}

	public int getSeasonIndex() {
		return seasonIndex;
	}

	public Collection<Episode> getEpisodes() {
		return episodes;
	}
}
