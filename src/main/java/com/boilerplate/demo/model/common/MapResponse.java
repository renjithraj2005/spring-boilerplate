package com.boilerplate.demo.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.HashMap;

@JsonInclude(Include.NON_NULL) 
public class MapResponse extends HashMap<String, String> {

	public static MapResponse of(String k, String v) {
		MapResponse res = new MapResponse();
		res.put(k, v);
		return res;
	}

	public MapResponse add(String k, String v) {
		put(k, v);
		return this;
	}

}
