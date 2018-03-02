package com.jeseromero.model.lightweight;

import com.jeseromero.core.model.TVSeries;

import java.util.Collection;

public class JSONLightSeries extends JSONable{

	private Collection<TVSeries> series;

	public JSONLightSeries(Collection<TVSeries> series) {
		this.series = series;
	}
}
