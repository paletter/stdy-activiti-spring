package com.paletter.stdy.activiti.spring.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.stereotype.Component;

import com.paletter.stdy.activiti.spring.CacheUtil;
import com.paletter.stdy.activiti.spring.dto.CreatePackageDTO;

@Component
public class PackageService {

	public void create(DelegateExecution de) {
		
		CreatePackageDTO d = de.getVariable("dto", CreatePackageDTO.class);
		System.out.println("## " + d.getGameName());
		System.out.println("## " + d.getPackageName());
	}
	
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
				System.out.println("## PackageNo:" + json.get("packageNo"));
			}
		}
		
		System.out.println("## Notify!");
	}
	
	public void manageAuditPass(DelegateExecution de) throws Exception {
		
		System.out.println("## Audit Pass!");
	}
	
	public void getState(DelegateExecution de) throws Exception {
		
		if (de.getVariable("params") != null) {
			String params = de.getVariable("params").toString();
			if (params.length() > 0) {
				JSONObject json = new JSONObject(params);
				System.out.println("## State:" + json.get("state"));
				System.out.println("## PackageNo:" + json.get("packageNo"));
			}
		}
		
		System.out.println("## Get Type!");
		de.setVariable("type", "1");
	}

	public void submitType2(DelegateExecution de) throws Exception {
		System.out.println("## Submit Type 2!");
	}
}
