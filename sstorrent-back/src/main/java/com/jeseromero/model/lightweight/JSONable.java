package com.jeseromero.model.lightweight;

import com.google.gson.Gson;

public abstract class JSONable {
	public String toJSON() {
		return new Gson().toJson(this);
	}
}
