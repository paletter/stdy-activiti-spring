package com.paletter.stdy.activiti.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.paletter.stdy.activiti.spring.CacheUtil;
import com.paletter.stdy.activiti.spring.dto.TaskDTO;

@Controller
public class PackageController {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/package/create/index")
	public ModelAndView indexCreatePackage() {
		ModelAndView mv = new ModelAndView("package/create_package");
		return mv;
	}
	
	@RequestMapping("/package/modify/index")
	public ModelAndView indexModifyPackage(String packageNo) {
		ModelAndView mv = new ModelAndView("package/modify_package");
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(packageNo)
				.processDefinitionKey("AuditPackageProcess").singleResult();
		String params = CacheUtil.get(pi.getId());
		JSONObject json = new JSONObject(params);
		mv.addObject("gameName", json.get("gameName"));
		mv.addObject("packageNo", json.get("packageNo"));
		return mv;
	}
	
	@RequestMapping("/package/audit/index")
	public ModelAndView indexAuditPackage() {
		ModelAndView mv = new ModelAndView("package/audit_package");
		List<Task> tasks = taskService.createTaskQuery().processDefinitionKeyLike("AuditPackageProcess").list();
		List<TaskDTO> taskDTOList = Lists.newArrayList();
		for (Task t : tasks) {
			TaskDTO dto = new TaskDTO();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
			dto.setTaskName(t.getName());
			dto.setProcessName(pi.getProcessDefinitionName());
			dto.setBusinessKey(pi.getBusinessKey());
			String params = CacheUtil.get(pi.getId());
			JSONObject json = new JSONObject(params);
			dto.setGameName(json.getString("gameName"));
			taskDTOList.add(dto);
		}
		mv.addObject("list", taskDTOList);
		return mv;
	}
	
	@RequestMapping("/package/start")
	@ResponseBody
	public String start(@RequestBody String params) {
		JSONObject json = new JSONObject(params);
		String packageNo = json.getString("packageNo");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("params", params);
		runtimeService.startProcessInstanceByKey("AuditPackageProcess", packageNo, variables);
		return "0";
	}
	
	@RequestMapping("/package/complete")
	@ResponseBody
	public String complete(@RequestBody String params) {
		JSONObject json = new JSONObject(params);
		String packageNo = json.getString("packageNo");
		Task task = taskService.createTaskQuery().processInstanceBusinessKeyLike(packageNo).singleResult();
		if (task != null) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("params", params);
			taskService.complete(task.getId(), variables);
		}
		return "0";
	}
}
