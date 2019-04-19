package com.paletter.stdy.activiti.spring.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import com.paletter.stdy.activiti.spring.dto.CreatePackageDTO;

@Service
public class PackageTaServiceImpl implements PackageTaService {

	public void audit(DelegateExecution de) {
		
		CreatePackageDTO d = de.getVariable("dto", CreatePackageDTO.class);
		System.out.println("## " + d.getGameName());
		System.out.println("## " + d.getPackageName());
	}
}
