package com.paletter.stdy.activiti.spring;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
public class Printer {

	public void print(DelegateExecution e) {
		System.out.println("Hello");
		System.out.println(e);
	}
}
