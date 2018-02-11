package com.jeseromero.model.lightweight;

import com.jeseromero.model.Request;

public class JSONLightRequest extends JSONable {
	private Request request;

	public JSONLightRequest(Request request) {
		this.request = request;
	}
}
