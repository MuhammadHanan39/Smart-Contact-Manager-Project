package com.smart.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.manager.dao.ProjectRepo;
import com.smart.manager.entities.User;
import com.smart.manager.service.EmailSenderServiceClass;
import com.smart.manager.service.ProjectServiceClass;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private ProjectServiceClass projectServiceClass;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ProjectRepo projectRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailSenderServiceClass emailSenderServiceClass;

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
		user.setUserImg("default.jpg");
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		try {
			this.projectServiceClass.save(user);
			model.addAttribute("registerSuccess", "You are successfully Registered.");
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

	@GetMapping("/Home")
	public String homeHandler() {
		System.out.println("Invoking Home Handler");
		return "Home";
	}

//	 Implementation of forgot password
	@GetMapping("/forgotPassword")
	public String getEmailFromUser(Model model) {
		model.addAttribute("pageName", "Get Email");
		return "getEmail";
	}

//   User enter the email after that application verifies it that user exist in database or not
	@PostMapping("/verifyEmailAndSendOtp")
	public String verifyEmailAndSendOtp(@RequestParam("username") String username, Model model, HttpSession session) {

		// Checking that user exist in database or not
		User user = this.projectRepo.findByUserName(username);

		// Storing username in the session
		session.setAttribute("username", username);
		System.out.println(username);

		// Checking that user exist or not
		if (user == null) {
			model.addAttribute("msg", "User with this email doesn't exist");
			return "redirect:/forgotPassword";

		} else {
			// Setting up the java mail api through which I will send the OTP to user's
			// email
			String subject = "OTP from smart contact manager";

			// Generating OTP
			long otp = (long) (Math.random() * (999999999 - 999 + 1) + 999);
			System.out.println("OTP of your account = " + otp);
			String text = "OTP = " + otp;

			// Adding OTP to session
			session.setAttribute("OTP", otp);

			// Sending Email
			boolean flag = this.emailSenderServiceClass.sendEmail(username, "hananshaikh20cs039@gmail.com", subject,
					text);
			if (flag) {
				model.addAttribute("msg", "We have sent you the OTP on your gmail");
				model.addAttribute("pageName", "Enter OTP");
				return "EnterOtp";
			} else {
				System.out.print("Something went wrong");
				return "redirect:/forgotPassword";
			}
		}
	}

	@PostMapping("/verifyOtpAndForgotPassword")
	public String sendForgotPasswordPage(@RequestParam("otp") String otp, Model model, HttpSession session) {

		// OTP send by user through form
		long userOtp = (long) Integer.parseInt(otp);
		System.out.println("User OTP" + userOtp);

		// Original OTP generated by system
		long originalOtp = (long) session.getAttribute("OTP");
		System.out.println("Original OTP" + originalOtp);

		if (userOtp == originalOtp) {
			model.addAttribute("pageName", "Enter Your New Pass");
			return "NewPass";
		} else {
			System.out.println("OTP is not valid");
			return "EnterOtp";
		}

	}

	// Method for changing the forgotted new password
	@PostMapping("/ChangePassword")
	public String changePassword(@RequestParam("newPass") String pass, HttpSession session) {
		// Getting the username that was entered when changing the password
		String username = (String) session.getAttribute("username");
		System.out.println("Username in change password method" + username);

	
		  User user=this.projectRepo.findByUserName(username);
		  user.setPassword(bCryptPasswordEncoder.encode(pass));
		  
		  this.projectRepo.save(user);
		 
		return "login";
	}

}
