package com.paletter.stdy.activiti.spring.controller;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.paletter.stdy.activiti.spring.dto.TaskDTO;

@Controller
public class ContractController {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/conrtact/create")
	@ResponseBody
	public String createContract(String contractNo) {
		runtimeService.startProcessInstanceByKey("contractAuditProcess", contractNo);
		return "0";
	}
	
	@RequestMapping("/conrtact/audit/finance")
	@ResponseBody
	public String auditFinance(String contractNo) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKeyLike(contractNo).taskCandidateGroup("finance").singleResult();
		if (task != null) {
			taskService.complete(task.getId());
		}
		return "0";
	}
	
	@RequestMapping("/conrtact/audit/legal")
	@ResponseBody
	public String auditLegal(String contractNo) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKeyLike(contractNo).taskCandidateGroup("legal").singleResult();
		if (task != null) {
			taskService.complete(task.getId());
		}
		return "0";
	}
	
	@RequestMapping("/conrtact/audit/finance/list")
	public ModelAndView finacneList() {
		ModelAndView mv = new ModelAndView("caf_list");
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("finance").list();
		List<TaskDTO> taskDTOList = Lists.newArrayList();
		for (Task t : tasks) {
			TaskDTO dto = new TaskDTO();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
			dto.setTaskName(t.getName());
			dto.setProcessName(pi.getProcessDefinitionName());
			dto.setBusinessKey(pi.getBusinessKey());
			taskDTOList.add(dto);
		}
		mv.addObject("taskList", taskDTOList);
		return mv;
	}
	
	@RequestMapping("/conrtact/audit/legal/list")
	public ModelAndView legalList() {
		ModelAndView mv = new ModelAndView("cal_list");
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("legal").list();
		List<TaskDTO> taskDTOList = Lists.newArrayList();
		for (Task t : tasks) {
			TaskDTO dto = new TaskDTO();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
			dto.setTaskName(t.getName());
			dto.setProcessName(pi.getProcessDefinitionName());
			dto.setBusinessKey(pi.getBusinessKey());
			taskDTOList.add(dto);
		}
		mv.addObject("taskList", taskDTOList);
		return mv;
	}
	
	@RequestMapping("/conrtact/create/index")
	public ModelAndView createContract() {
		ModelAndView mv = new ModelAndView("create_contract");
		return mv;
	}
	
}
