package com.paletter.stdy.activiti.spring.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.stereotype.Component;

import com.paletter.stdy.activiti.spring.CacheUtil;

@Component
public class PackageService {

	
	public void submitPackage(DelegateExecution de) throws Exception {

		String params = de.getVariable("params").toString();
		
		JSONObject json = new JSONObject(params);
		System.out.println("## PackageNo:" + json.get("packageNo"));
		System.out.println("## GameName:" + json.get("gameName"));

		CacheUtil.set(de.getProcessInstanceId(), params);
	}
	
	public void manageAudit(DelegateExecution de) throws Exception {
		
		if (de.getVariable("params") != null) {
			String params = de.getVariable("params").toString();
			if (params.length() > 0) {
				JSONObject json = new JSONObject(params);
				System.out.println("## State:" + json.get("state"));
			}
		}
		
		System.out.println("Notify!");
	}

}
