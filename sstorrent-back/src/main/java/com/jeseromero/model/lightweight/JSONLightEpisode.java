package com.jeseromero.model.lightweight;

import com.jeseromero.core.model.Episode;
import com.jeseromero.core.model.SubtitleGroup;

import java.util.ArrayList;
import java.util.Collection;

public class JSONLightEpisode extends JSONable {

	private final String name;

	private final Collection<JSONLightSubtitleGroup> subtitlesGroup;

	public JSONLightEpisode(Episode episode) {
		this.name = episode.getName();

		this.subtitlesGroup = new ArrayList<>();

		for (SubtitleGroup subtitleGroup : episode.getSubtitlesGroups()) {
			this.subtitlesGroup.add(new JSONLightSubtitleGroup(subtitleGroup));
		}
	}
}
