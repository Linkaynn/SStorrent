package com.jeseromero.model.lightweight;

public class SResponse extends JSONable{
	private String status;
	private Object data;

	public SResponse(String status) {
		this(status, null);
	}

	public SResponse(String status, JSONable data) {
		this.status = status;
		this.data = data;
	}
}
