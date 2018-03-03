package com.jeseromero.core.managers;

import com.jeseromero.core.requesters.SubtitleRequester;
import com.jeseromero.core.model.Season;
import com.jeseromero.core.model.TVSeries;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.util.ArrayList;
import java.util.Collection;

public class SubtitleManager {

	public static final double DISTANCE_THRESHOLD = 0.84;

	private static Collection<TVSeries> series = null;

	private final SubtitleRequester subtitleRequester;

	public static void init() {
		series = new SubtitleRequester().getAllSeries();
	}

	public static boolean isInitialized() {
		return series != null && series.size() > 0;
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

				Double distance = new JaroWinklerDistance().apply(tvSeriesName, valueLowedCase);

				if (distance >= DISTANCE_THRESHOLD) {
					tvSeries.setDistance(distance);
					filtered.add(tvSeries);
					break;
				}
			}

		}

		return filtered;
	}

	public Collection<Season> getSeasonsOf(String seriesID) {
		Collection<Season> seasons = new ArrayList<>();

		TVSeries series = getSeries(seriesID);

		if (series != null) {
			for (int i = 1; i <= series.getSeasons(); i++) {
				seasons.add(subtitleRequester.getSeason(seriesID, i));
			}
		}

		return seasons;
	}

	private TVSeries getSeries(String seriesID) {
		for (TVSeries tvSeries : series) {
			if (tvSeries.getId().equals(seriesID)) {
				return tvSeries;
			}
		}

		return null;
	}
}
