package com.jeseromero.core;


import com.jeseromero.core.controller.TorrentSearcher;
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
		TorrentSearcher torrentSearcher = new TorrentSearcher(new EliteTorrentConfiguration());

		assertThat(torrentSearcher.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}

	@Test
	public void limetorrent_works() {
		TorrentSearcher torrentSearcher = new TorrentSearcher(new LimeTorrentsConfiguration());

		assertThat(torrentSearcher.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}

	@Test
	public void magnetlink_works() {
		TorrentSearcher torrentSearcher = new TorrentSearcher(new MagnetLinkConfiguration());

		assertThat(torrentSearcher.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}

	@Test
	public void piratebay_works() {
		TorrentSearcher torrentSearcher = new TorrentSearcher(new PirateBayConfiguration());

		assertThat(torrentSearcher.search("Doctor who", 0).size(), Matchers.greaterThan(0));
	}
}
