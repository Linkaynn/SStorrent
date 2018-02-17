package com.jeseromero.model.lightweight;

public class JSONLightError extends JSONable{

	private final int id;
	private final String error;

	public JSONLightError(int id, String error) {
		this.id = id;
		this.error = error;
	}
}
