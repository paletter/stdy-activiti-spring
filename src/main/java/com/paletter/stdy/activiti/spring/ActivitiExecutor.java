package com.paletter.stdy.activiti.spring;

import java.lang.reflect.Method;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ActivitiExecutor {

	@Autowired
	private ApplicationContext applicationContext;
	
	public void execute(DelegateExecution de, String beanName, String method) {
		
		Object bean = applicationContext.getBean(beanName);
		
		for (Method m : bean.getClass().getMethods()) {
			if (m.getName().equals(method)) {
				
			}
		}
	}
}
