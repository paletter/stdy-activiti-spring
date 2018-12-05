package com.paletter.stdy.activiti.spring;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class Test implements TaskListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6752854803223775233L;

	@Override
	public void notify(DelegateTask dt) {
		System.out.println("## Notify " + dt.getEventName());
	}

	
}
