package com.jeseromero.model.lightweight;

import com.jeseromero.model.Mirror;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSONLightMirrors extends JSONable {
	private List<JSONLightMirror> mirrors = new ArrayList<>();

	public JSONLightMirrors(Set<Mirror> mirrors) {
		super();

		for (Mirror mirror : mirrors) {
			this.mirrors.add(new JSONLightMirror(mirror));
		}
	}
}
