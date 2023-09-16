package com.smart.manager.entities;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Size(min = 3,max = 20,message = "Name size must in between 3 and 20")
	@NotBlank(message = "Name field cant be null")
	private String userName;
	@Email
	@NotBlank
	private String userEmail;
	@NotBlank(message = "Please provide your password")
	private String password;
	@Column(length = 200)
	private String about;
	private String userImg;
	private boolean enable;
	private String role;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY ,mappedBy = "user")
	private List<Contacts> contacts;
	
	
	
//	Generating getters and setters of User class fields
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Contacts> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	


	
	
	
	
}
