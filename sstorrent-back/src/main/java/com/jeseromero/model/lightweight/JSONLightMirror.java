package com.jeseromero.model.lightweight;

import com.jeseromero.model.Mirror;

public class JSONLightMirror extends JSONable{

	private final String name;

	private final String language;

	public JSONLightMirror(Mirror mirror) {
		this.name = mirror.getName();
		this.language = mirror.getLanguage();
	}
}
