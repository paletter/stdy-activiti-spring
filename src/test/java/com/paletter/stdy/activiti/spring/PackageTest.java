package com.paletter.stdy.activiti.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.paletter.stdy.activiti.spring.controller.PackageController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class PackageTest {

	@Autowired
	private PackageController packageController;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private TaskService taskService;
	
	String packageNo = "PN201826735";
	
	@Test
	public void testStart() {
		JSONObject json = new JSONObject();
		json.put("packageNo", "p001");
		json.put("gameName", "1");
		packageController.start(json.toString());
		
		List<Task> tasks = taskService.createTaskQuery().list();
		for (Task t : tasks) {
			System.out.println("-- task:" + t.getName());
			System.out.println("-- task:" + t.getDelegationState());
			System.out.println("-- task:" + t.getProcessDefinitionId());
		}
	}
	
	@Test
	public void test() {
		try {
			
			JSONObject json = new JSONObject();
			json.put("packageNo", packageNo);
			json.put("gameName", "1");
			packageController.start(json.toString());
	
			Task task = taskService.createTaskQuery().processInstanceBusinessKeyLike(packageNo).singleResult();
			if (task != null) {
				json = new JSONObject();
				json.put("state", 1);
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("params", json.toString());
				taskService.complete(task.getId(), variables);
			}
	
			List<Task> tasks = taskService.createTaskQuery().list();
			for (Task t : tasks) {
				System.out.println("-- task:" + t.getName());
				System.out.println("-- task:" + t.getDelegationState());
				System.out.println("-- task:" + t.getProcessDefinitionId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			testDeleteProcessInstance();
		}
	}
	
	@Test
	public void testComplete() {
		JSONObject json = new JSONObject();
		json.put("packageNo", packageNo);
		json.put("gameName", "FGO2");
		json.put("state", "1");
//		packageController.complete(packageNo, json.toString());
//		Task task = taskService.createTaskQuery().processInstanceBusinessKeyLike(packageNo).singleResult();
//		if (task != null) {
//			Map<String, Object> variables = new HashMap<String, Object>();
//			variables.put("state", 1);
//			JSONObject json = new JSONObject();
//			json.put("packageNo", packageNo);
//			json.put("gameName", "FGO2");
//			variables.put("params", json.toString());
//			taskService.complete(task.getId(), variables);
//		}

		List<Task> tasks = taskService.createTaskQuery().list();
		for (Task t : tasks) {
			System.out.println("-- task:" + t.getName());
			System.out.println("-- task:" + t.getDelegationState());
			System.out.println("-- task:" + t.getProcessDefinitionId());
		}
	}
	
	@Test
	public void testDeleteProcessInstance() {
		List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().list();
		for (ProcessInstance p : l) {
			runtimeService.deleteProcessInstance(p.getId(), "");
			historyService.deleteHistoricProcessInstance(p.getId());
		}
	}
	
	@Test
	public void testProcessInstance() {
		List<ProcessInstance> l = runtimeService.createProcessInstanceQuery().list();
		System.out.println(l.size());
	}
	
	@Test
	public void testShowTask() {
		List<Task> tasks = taskService.createTaskQuery().processDefinitionKeyLike("AuditPackageProcess").list();
		for (Task t : tasks) {
			System.out.println("-- task:" + t.getName());
			System.out.println("-- task:" + t.getDelegationState());
			System.out.println("-- task:" + t.getProcessDefinitionId());
		}
	}
}
