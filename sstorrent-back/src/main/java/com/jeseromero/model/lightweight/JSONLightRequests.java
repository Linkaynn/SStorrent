package com.jeseromero.model.lightweight;

import com.jeseromero.model.Request;

import java.util.List;

public class JSONLightRequests extends JSONable {

	private List<Request> requests;

	public JSONLightRequests(List<Request> requests) {
		this.requests = requests;
	}
}
