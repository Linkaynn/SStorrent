package com.jeseromero.core;


import com.jeseromero.core.torrent.TorrentManager;
import com.jeseromero.core.model.configurations.EliteTorrentConfiguration;
import com.jeseromero.core.model.configurations.LimeTorrentsConfiguration;
import com.jeseromero.core.model.configurations.MagnetLinkConfiguration;
import com.jeseromero.core.model.configurations.PirateBayConfiguration;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigurationTest {

	@Test
	public void elitetorrent_works() {
		TorrentManager torrentManager = new TorrentManager(new EliteTorrentConfiguration());

		assertThat(torrentManager.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}

	@Test
	public void limetorrent_works() {
		TorrentManager torrentManager = new TorrentManager(new LimeTorrentsConfiguration());

		assertThat(torrentManager.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}

	@Test
	public void magnetlink_works() {
		TorrentManager torrentManager = new TorrentManager(new MagnetLinkConfiguration());

		assertThat(torrentManager.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}

	@Test
	public void piratebay_works() {
		TorrentManager torrentManager = new TorrentManager(new PirateBayConfiguration());

		assertThat(torrentManager.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}
}
