package com.smart.manager.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.manager.dao.ContactRepo;
import com.smart.manager.dao.ProjectRepo;
import com.smart.manager.entities.Contacts;
import com.smart.manager.entities.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	ProjectRepo projectRepo;
	@Autowired
	ContactRepo contactRepo;

	// This method invokes everytime when /user/ request is encountered
	@ModelAttribute
	public void addCommonData(Principal principal, Model model) {
		String userName = principal.getName();
		User user = this.projectRepo.findByUserName(userName);

		model.addAttribute("user", user);

	}

	@GetMapping("/dashboard")
	public String dashboardHandler(Principal principal, Model model) {

		String userName = principal.getName();
		User user = this.projectRepo.findByUserName(userName);

		model.addAttribute("pageName", user.getUserName() + "-Dashboard");

		System.out.println("Dashboard Handler");
		return "user/dashboard";

	}
	
	
//	==================== Handler for showing the Contact Form ===============

	@GetMapping("/AddContactForm")
	public String addContactFormHandler(Model model) {

		model.addAttribute("contact", new Contacts());
		model.addAttribute("pageName", "AddContact");
		System.out.println("addContactsHandler");
		return "user/addContact";
	}

	
	
	/*
	 * =========== Adding all the details of the contact in to database ================
	 */
	@PostMapping("/addContactDetails")
	public String addContactHandler(@Valid @ModelAttribute Contacts contact, BindingResult result, Principal principal,
			Model model, @RequestParam("contactImg") MultipartFile file) {

		model.addAttribute("contact", contact);

		if (result.hasErrors()) {
			System.out.println(result.getFieldError("name").getDefaultMessage());
			// System.out.println(result.getFieldError("number").getDefaultMessage());
			System.out.println(result.getAllErrors());
			System.out.print("Ha ustad");
			return "user/addContact";
		}

		System.out.println(contact);
		String userName = principal.getName();
		User user = this.projectRepo.findByUserName(userName);
		if(file.isEmpty()) {
			System.out.print("FIle is empty");
			contact.setImg("default.jpg");
		}
		else {
		try {
			String contactImgName = file.getOriginalFilename();
			contact.setImg(contactImgName);

			File saveFile = new ClassPathResource("/static/images/").getFile();
			Path path = (Path) Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println(path);

		} catch (Exception e) {
			System.out.println("Hello" + e);

		}
		
		}

		contact.setUser(user);
		user.getContacts().add(contact);

		this.projectRepo.save(user);

		model.addAttribute("contactAdded", "Contact is Successfully added");

		return "user/addContact";
	}

	// ============== Handler for showing all the contacts stored with the
	// particular user ============
	@GetMapping("/showContacts/{pgNo}")
	public String showContactsHandler(@PathVariable("pgNo") int pgNo, Model model, Principal principal) {

		System.out.println("Welcome to Show Contact Handler");

		// Finding user that is online and using it we are getting its contacts
		User user = projectRepo.findByUserName(principal.getName());

		// getting all the contacts of the user that is online
		// List<Contacts> list = user.getContacts();

		// Pageable Object for current Page and Size of page=5
		Pageable pageable = PageRequest.of(pgNo - 1, 5);

		Page<Contacts> contact = this.contactRepo.findAllContacts(user.getUserId(), pageable);

		// List<Contacts> contacts = pages.getContent();

		System.out.println(contact);
		System.out.println(contact.getContent());

		model.addAttribute("CurrentPageNo", pgNo);
		model.addAttribute("TotalPages", contact.getTotalPages());
		model.addAttribute("contacts", contact.getContent());

		// Checking if the list of contacts is empty that it pop up the msg for adding
		// the contact
		/*
		 * if(contact.isEmpty()) { boolean noContact=true;
		 * model.addAttribute("zeroContacts",noContact); model.addAttribute(
		 * "AddContacts","You dont have any contact stored! Click the Add Contact Button to add."
		 * ); } else { model.addAttribute("contacts",contact); }
		 */

		// Sending the page name to base page
		model.addAttribute("pageName", "Show Contact -Page");

		return "user/AllContacts";
	}

	/*
	 * =========================== Handler For Showing All the Details of the Contact =================
	 */

	@GetMapping("/AllContactDetails/{cId}")
	public String AllDetailsHandler(@PathVariable("cId") int cId, Model model) {
		System.out.println("All Details Handler");
		Contacts contact = contactRepo.getContact(cId);
		model.addAttribute("contact",contact);
		return "/user/ContactAllDetail";
	}
	
	
	/*
	 *======================== Deleting the Contact ==========================
	 */
	@GetMapping("/deleteContact/{deleteId}")
	public String deleteHandler(@PathVariable("deleteId")Integer deleteId) {
		
		Contacts c=this.contactRepo.getContact(deleteId);
		c.setUser(null);
		System.out.println("Deleted Contact Id"+deleteId);
		this.contactRepo.deleteContact(deleteId);
		System.out.println("Contact Deleted");
		return "redirect:/user/showContacts/1";
	}
	
	
	
//	======================= Getting the Contact For Modification ==========================
	@PostMapping(value="/update/{id}")
	public String updateContact(@PathVariable("id") int id,Model model) {
		
		
		Contacts c = this.contactRepo.getContact(id);
		
		model.addAttribute("title","Update-Contact");
		model.addAttribute("contact",c);
		
		return "/user/Update-Contact";
	}
	
	
	
	/* ============================= Updating the Contact ==================== */
	@PostMapping("/Update-Contact")
	public String updatingContact(@Valid @ModelAttribute Contacts contact, BindingResult result,
			Model m, @RequestParam("contactImg") MultipartFile file) {
		
		m.addAttribute("contactUpdated","Contact is successfully updated");
		m.addAttribute("contact",contact);
		
		
//		=================== Validation Check ====================
		if (result.hasErrors()) {
			
			System.out.println(result.getAllErrors());
			System.out.print("Ha ustad");
			return "user/Update-Contact";
		}
		
		
		
//		=========== If file is empty then It img remains same as previous ============
		if(file.isEmpty()) {
			System.out.print("File remain same");
			
		}
		else {
		try {
			String contactImgName = file.getOriginalFilename();
			contact.setImg(contactImgName);

			File saveFile = new ClassPathResource("/static/images/").getFile();
			Path path = (Path) Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println(path);

		} catch (Exception e) {
			System.out.println("Hello" + e);

		}
		
		}
		
		
		return "user/Update-Contact";
	}
	
	
	

}
