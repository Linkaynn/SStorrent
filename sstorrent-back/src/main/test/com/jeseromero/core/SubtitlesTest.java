package com.jeseromero.core;


import com.jeseromero.core.managers.SubtitleManager;
import com.jeseromero.core.model.Episode;
import com.jeseromero.core.model.Season;
import com.jeseromero.core.model.TVSeries;
import com.jeseromero.util.SearchUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;

public class SubtitlesTest {

	@Before
	public void setUp() {
		SubtitleManager.init();
	}

	@Test
	public void must_recover_nearest_name_series() {
		Collection<String> values = SearchUtil.torrentNameDecompose("DCs.Legends.of.Tomorrow.S03E12.HDTV.x264-SVA");

		Collection<TVSeries> series = new SubtitleManager().searchBy(values);

		assertThat(series.size(), is(2));
	}

	@Test
	public void must_series_link() {
		Collection<String> values = SearchUtil.torrentNameDecompose("DCs.Legends.of.Tomorrow.S03E12.HDTV.x264-SVA");

		SubtitleManager subtitleManager = new SubtitleManager();

		Collection<TVSeries> series = subtitleManager.searchBy(values);

		TVSeries tvSeries = series.iterator().next();

		for (Season season : subtitleManager.getSeasonsOf(tvSeries.getId())) {
			for (Episode episode : season.getEpisodes()) {
				System.out.println(episode.getName());
			}
		}

	}

}
