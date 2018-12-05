package com.paletter.stdy.activiti.spring;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheUtil {

	static Cache<String, String> cache;
	
	static {
		cache = CacheBuilder.newBuilder()
		    .maximumSize(1000)
		    .build();
	}
	
	public static void set(String key, String val) {
		cache.put(key, val);
	}
	
	public static String get(String key) {
		return cache.getIfPresent(key);
	}
	
}
