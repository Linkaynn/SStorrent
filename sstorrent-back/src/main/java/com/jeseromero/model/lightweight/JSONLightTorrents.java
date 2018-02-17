package com.jeseromero.model.lightweight;

import java.util.ArrayList;
import java.util.List;

public class JSONLightTorrents extends JSONable {

	private List<JSONLightTorrent> lightTorrents;

	public JSONLightTorrents() {
		this.lightTorrents = new ArrayList<>();
	}

	public JSONLightTorrents(List<JSONLightTorrent> lightTorrents) {
		this.lightTorrents = lightTorrents;
	}
}
