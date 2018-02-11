package com.jeseromero.model.lightweight;

import com.google.gson.Gson;

public class JSONError extends JSONable{
	private final int id;
	private final String error;

	public JSONError(int id, String error) {
		this.id = id;
		this.error = error;
	}

	public int getId() {
		return id;
	}

	public String getError() {
		return error;
	}
}
