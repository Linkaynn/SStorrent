package com.jeseromero.core.managers;

import com.jeseromero.core.requesters.SubtitleRequester;
import com.jeseromero.core.model.Season;
import com.jeseromero.core.model.TVSeries;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.util.ArrayList;
import java.util.Collection;

public class SubtitleManager {

	public static final double DISTANCE_THRESHOLD = 0.95;

	private static Collection<TVSeries> series = null;

	private final SubtitleRequester subtitleRequester;

	public static void init() {
		series = new SubtitleRequester().getAllSeries();
	}

	public SubtitleManager() {
		subtitleRequester = new SubtitleRequester();
	}

	public Collection<TVSeries> searchBy(Collection<String> values) {
		Collection<TVSeries> filtered = new ArrayList<>();

		for (TVSeries tvSeries : series) {
			String tvSeriesName = tvSeries.getName().toLowerCase();

			for (String value : values) {
				String valueLowedCase = value.toLowerCase();

				if (new JaroWinklerDistance().apply(tvSeriesName, valueLowedCase) >= DISTANCE_THRESHOLD) {
					filtered.add(tvSeries);
					break;
				}
			}

		}

		return filtered;
	}

	public Collection<Season> getSeasonsOf(TVSeries tvSeries) {
		Collection<Season> seasons = new ArrayList<>();

		for (int i = 1; i <= tvSeries.getSeasons(); i++) {
			seasons.add(subtitleRequester.getSeason(tvSeries, i));
		}

		return seasons;
	}
}
