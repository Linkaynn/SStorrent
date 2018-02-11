package com.jeseromero.model.lightweight;

import com.google.gson.Gson;

public class SResponse extends JSONable{
	private String status;
	private Object data;

	public SResponse(String status, JSONable data) {
		this.status = status;
		this.data = data;
	}
}
