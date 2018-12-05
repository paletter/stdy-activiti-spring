package com.paletter.stdy.activiti.spring;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.paletter.stdy.activiti.spring.dto.TaskDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class ActivitiTest {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Test
	public void testProcessInstanceList() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
		System.out.println(processInstances.size());
	}
	
	@Test
	public void testProcessDefinitionList() {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processList = processDefinitionQuery.list();
		System.out.println(processList.size());
	}
	
	@Test
	public void testTaskList() {
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("finance").list();
		System.out.println(tasks.size());
		for (Task t : tasks) {
			TaskDTO dto = new TaskDTO();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
			dto.setTaskName(t.getName());
			dto.setProcessName(pi.getProcessDefinitionName());
			dto.setBusinessKey(pi.getBusinessKey());
			
			System.out.println(new JSONObject(dto).toString());
		}
	}
	
}
