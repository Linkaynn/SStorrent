package com.jeseromero.model.lightweight;

import com.jeseromero.core.model.Episode;
import com.jeseromero.core.model.Season;

import java.util.ArrayList;
import java.util.Collection;

public class JSONLightSeason extends JSONable {

	private final int index;

	private final Collection<JSONLightEpisode> episodes;

	public JSONLightSeason(Season season) {
		this.index = season.getSeasonIndex();

		this.episodes = new ArrayList<>();

		for (Episode episode : season.getEpisodes()) {
			this.episodes.add(new JSONLightEpisode(episode));
		}
	}
}
