package com.paletter.stdy.activiti.spring.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.paletter.stdy.activiti.spring.dto.TaskDTO;

@Controller
public class ActivitiController {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private TaskService taskService;
	
    @RequestMapping(value = "/process/list")
    public ModelAndView processList() {
        ModelAndView mav = new ModelAndView("process_list");
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processList = processDefinitionQuery.list();
        mav.addObject("list", processList);
        return mav;
    }
    
    @RequestMapping(value = "/model/list")
    public ModelAndView modelList() {
    	ModelAndView mav = new ModelAndView("model_list");
    	List<Model> list = repositoryService.createModelQuery().list();
    	mav.addObject("list", list);
    	return mav;
    }
    
    @RequestMapping(value = "/task/list")
    public ModelAndView taskList() {
    	ModelAndView mav = new ModelAndView("task_list");
    	List<Task> list = taskService.createTaskQuery().list();
    	List<TaskDTO> taskDTOList = Lists.newArrayList();
		for (Task t : list) {
			TaskDTO dto = new TaskDTO();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
			dto.setTaskName(t.getName());
			dto.setProcessName(pi.getProcessDefinitionName());
			dto.setBusinessKey(pi.getBusinessKey());
			taskDTOList.add(dto);
		}
    	mav.addObject("list", taskDTOList);
    	return mav;
    }
	
	@RequestMapping("/show")
	@ResponseBody
	public void show(String key, HttpServletResponse response) throws Throwable {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(key).singleResult();
		if (processInstance != null) {
		
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getId());
			
		    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
		    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());
	
	        ProcessDiagramGenerator diagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
	        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

	        byte[] b = new byte[1024];
	        int len;
	        while ((len = imageStream.read(b, 0, 1024)) != -1) {
	            response.getOutputStream().write(b, 0, len);
	        }
		}
	}
	
}
