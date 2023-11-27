package com.smart.manager.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.smart.manager.dao.ContactRepo;
import com.smart.manager.dao.ProjectRepo;
import com.smart.manager.entities.Contacts;
import com.smart.manager.entities.User;

@RestController
public class SearchController {

	
	@Autowired
	public ProjectRepo projectRepo;
	
	@Autowired
	public ContactRepo contactRepo;
	
	
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchResult(@PathVariable("query") String query,Principal principal){
		User user = this.projectRepo.findByUserName(principal.getName());
		List<Contacts> contacts =this.contactRepo.findByNameContainingAndUser(query, user);
		return ResponseEntity.ok(contacts);
	}
	
	
	
}
