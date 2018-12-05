package com.paletter.stdy.activiti.spring;

import java.util.List;

import javax.annotation.PostConstruct;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartUp {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@PostConstruct
	public void execute() {
		System.out.println("StartUP!");
		List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().list();
		for (ProcessInstance p : l) {
			runtimeService.deleteProcessInstance(p.getId(), "");
			historyService.deleteHistoricProcessInstance(p.getId());
		}
	}
}
