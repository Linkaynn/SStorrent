package com.jeseromero.model.lightweight;

import java.util.List;

public class LightMirrors extends JSONable {
	private List<String> mirrors;

	public LightMirrors(List<String> mirrors) {
		super();
		this.mirrors = mirrors;
	}
}
