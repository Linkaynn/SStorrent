package com.jeseromero.model.lightweight;

import java.util.List;

public class JSONLightTorrents extends JSONable {
	private List<JSONLightTorrent> lightTorrents;

	public JSONLightTorrents(List<JSONLightTorrent> lightTorrents) {
		super();
		this.lightTorrents = lightTorrents;
	}
}
