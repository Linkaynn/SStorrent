package com.jeseromero.model.lightweight;

import com.jeseromero.core.model.Season;

import java.util.ArrayList;
import java.util.Collection;

public class JSONLightSeasons extends JSONable {

	private Collection<JSONLightSeason> seasons;

	public JSONLightSeasons(Collection<Season> seasons) {
		this.seasons = new ArrayList<>();

		for (Season season : seasons) {
			this.seasons.add(new JSONLightSeason(season));
		}
	}
}
