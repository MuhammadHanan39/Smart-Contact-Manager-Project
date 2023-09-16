package com.smart.manager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.manager.dao.ProjectRepo;
import com.smart.manager.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ProjectRepo projectRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.projectRepo.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid Email");
		}
		
		return new UserDetailsImpl(user);
	}

}
