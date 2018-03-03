package com.jeseromero.core.requesters;

import com.jeseromero.core.model.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;

public class SubtitleRequester {

	public Collection<TVSeries> getAllSeries() {
		ArrayList<TVSeries> series = new ArrayList<>();

		Document document = new HTTPRequest().getHtmlDocument("https://www.tusubtitulo.com/series.php");

		Elements allTVSeriesRaw = document.select("td.line0 > a");

		for (Element tvSeriesRaw : allTVSeriesRaw) {
			series.add(new TVSeries(tvSeriesRaw.text(), tvSeriesRaw.attr("href").split("/")[2]));
		}

		Elements allTVSeriesData = document.select("td.newsDate");

		for (int i = 0; i < allTVSeriesData.size(); i++) {
			series.get(i).setSeasons(Integer.parseInt(allTVSeriesData.get(i).text().split(" ")[0]));
		}

		return series;
	}

	public Season getSeason(String seriesID, int seasonIndex) {
		Season season = new Season(seasonIndex);

		Document document = new HTTPRequest().getHtmlDocument("https://www.tusubtitulo.com/ajax_loadShow.php?show=" + seriesID + "&season=" + seasonIndex);

		Elements groups = document.select("tbody");

		for (Element episodeGroup : groups) {

			Episode episode = new Episode(episodeGroup.select("tr > .NewsTitle > a").text());

			Elements subtitleRows = episodeGroup.select("tr");

			subtitleRows.remove(0); // This is the episode name
			subtitleRows.remove(subtitleRows.size() - 1); // This is a blank row

			SubtitleGroup subtitleGroup = null;

			for (Element subtitleRow : subtitleRows) {

				Elements subtitleTypeElement = subtitleRow.select("td[colspan=\"3\"]");

				if (!subtitleTypeElement.isEmpty()) {
					if (subtitleGroup != null) {
						episode.add(subtitleGroup);
					}

					subtitleGroup = new SubtitleGroup(subtitleTypeElement.text());
					continue;
				}

				if (subtitleGroup != null) {
					Elements subtitleData = subtitleRow.select("td");

					if (subtitleData.size() > 6 && subtitleData.get(5).text().trim().toLowerCase().equals("completado")) {
						String language = subtitleData.get(4).text();
						String link = subtitleData.get(6).select("a").attr("href").substring(2);

						subtitleGroup.add(new Subtitle(language, link));
					}
				}
			}

			if (subtitleGroup != null) {
				episode.add(subtitleGroup);
			}

			season.add(episode);
		}

		return season;
	}
}
