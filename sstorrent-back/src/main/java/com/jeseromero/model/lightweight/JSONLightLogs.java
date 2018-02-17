package com.jeseromero.model.lightweight;

import com.jeseromero.model.Log;

import java.util.List;

public class JSONLightLogs extends JSONable {
	private List<Log> logs;

	public JSONLightLogs(List<Log> logs) {
		this.logs = logs;
	}
}
