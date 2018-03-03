package com.jeseromero.model.lightweight;

import com.jeseromero.core.model.Subtitle;
import com.jeseromero.core.model.SubtitleGroup;

import java.util.ArrayList;
import java.util.Collection;

public class JSONLightSubtitleGroup extends JSONable {

	private final String type;

	private final Collection<JSONLightSubtitle> subtitles;

	public JSONLightSubtitleGroup(SubtitleGroup subtitleGroup) {
		this.type = subtitleGroup.getType();

		this.subtitles = new ArrayList<>();

		for (Subtitle subtitle : subtitleGroup.getSubtitles()) {
			this.subtitles.add(new JSONLightSubtitle(subtitle));
		}
	}
}
