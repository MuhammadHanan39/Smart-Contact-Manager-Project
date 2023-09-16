package com.smart.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.manager.entities.User;
import com.smart.manager.service.ProjectServiceClass;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private ProjectServiceClass projectServiceClass;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	

//	Handler for Mapping of Registration page
	@GetMapping("/do-register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		System.out.println("We are in Register handler");

		return "signup";
			
	}

	
	
	
	
//	Handler after Form Details are submitted
	@PostMapping("/dashboard")
	public String submitHandler(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam(value = "agreement", required = false) boolean agreement, Model model) {

		System.out.println("We are now in submit handler");

		// Sending the submitted data to HTTP
		model.addAttribute("user", user);

		// checking that the form contains error or not
		if (result.hasErrors()) {
			System.out.println("form fields has error");
			System.out.println(result.getAllErrors());
			return "signup";
		}

		// Checking that the User checks the terms or not
		if (!agreement) {
			System.out.println("agreement checking..");
			model.addAttribute("doCheck", "please agree the terms and conditions");
			return "signup";
		}

		// Now when everything is fine now saving the data in to database
		user.setEnable(true);
		user.setRole("ROLE_USER");
		user.setUserImg("default.img");
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		try {
			this.projectServiceClass.save(user);
			model.addAttribute("registerSuccess","You are successfully Registered.");
		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("signupException", "You are already Logged in");
			return "signup";
		}

		return "signup";
	}

	// Handler for Login Page of Spring Security

	
	
	
	
	
	@GetMapping("/login")
	public String loginHandler(Model model) {
		model.addAttribute("pageName", "Login Page");
		System.out.println("Login Handler");
		return "login";
	}

	
	
	
	/*
	 * @GetMapping("/logout") public String logoutHandler(Model model) {
	 * 
	 * model.addAttribute("pageName", "Logout Page"); return "logout"; }
	 */

	
	 @GetMapping("/Home")
	 public String homeHandler() {
		 System.out.println("Invoking Home Handler");
		 return "Home";
	 }
	
	
	

	
	
	


}
