package com.smart.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.manager.dao.ProjectRepo;
import com.smart.manager.entities.User;

@Service
public class ProjectServiceClass {

	@Autowired
	private ProjectRepo projectRepo;
	
	public User save(User user)throws Exception{
		
		
		User u=this.projectRepo.findByUserName(user.getUserEmail());
		if(u!=null) {
			throw new Exception("You are already Signed in");
		}
		
		User resUser= this.projectRepo.save(user);
		return resUser;
	}
	
	
	
}
