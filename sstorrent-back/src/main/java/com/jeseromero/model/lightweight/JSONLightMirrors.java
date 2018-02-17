package com.jeseromero.model.lightweight;

import java.util.List;

public class JSONLightMirrors extends JSONable {
	private List<String> mirrors;

	public JSONLightMirrors(List<String> mirrors) {
		super();
		this.mirrors = mirrors;
	}
}
