package com.paletter.stdy.activiti.spring;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JSONUtil {

	public Object get(String json, String key) {
		return new JSONObject(json).get(key);
	}
}
