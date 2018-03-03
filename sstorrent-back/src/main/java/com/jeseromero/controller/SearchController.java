package com.jeseromero.controller;


import com.jeseromero.core.managers.SubtitleManager;
import com.jeseromero.core.managers.TorrentManager;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.Search;
import com.jeseromero.model.User;
import com.jeseromero.model.lightweight.JSONLightLink;
import com.jeseromero.model.lightweight.JSONLightSeasons;
import com.jeseromero.model.lightweight.JSONLightSeries;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.util.SearchUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collection;

public class SearchController {

	private static SearchController instance;

	private MirrorController mirrorController = MirrorController.instance();

	public static SearchController instance() {
		if (instance == null) {
			instance = new SearchController();
		}

		return instance;
	}

	public Collection<Torrent> search(String mirror, String value, int index) {
		Configuration mirrorConfig = mirrorController.getConfiguration(mirror);

		if (mirrorConfig == null) return new ArrayList<>();

		return new TorrentManager(mirrorConfig).search(value, index);
	}

	public JSONLightLink retrieveLink(String link, String mirror) {
		Configuration mirrorConfig = mirrorController.getConfiguration(mirror);

		if (mirrorConfig == null) return null;

		return new TorrentManager(mirrorConfig).retrieveLink(link);
	}

	public void registerSearch(User user, String value) throws IllegalStateException {

		Session session = DBSessionFactory.getSession();

		try {
			Transaction transaction = session.beginTransaction();
			session.save(new Search(user, value));
			transaction.commit();

			user.refresh();
		} catch (Exception e) {
			throw new IllegalStateException("Error registering the search", e);
		}

	}

	public JSONLightSeries getApproachSeriesOf(String torrentName) {
		return new JSONLightSeries(new SubtitleManager().searchBy(SearchUtil.torrentNameDecompose(torrentName)));
	}

	public JSONLightSeasons getSeasonsOf(String seriesID) {
		return new JSONLightSeasons(new SubtitleManager().getSeasonsOf(seriesID));
	}
}
