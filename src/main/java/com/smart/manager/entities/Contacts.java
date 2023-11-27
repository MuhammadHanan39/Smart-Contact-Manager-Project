package com.smart.manager.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Contacts {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	
	@NotBlank(message = "Please enter the name")
	private String name;
	private String description;
	 
	@Email
	private String contactEmail;
	@Min(value = 10,message = "Invalid Number!")
	private long number;
	private String nickname;
	private String work;
	private String img;
	@JsonIgnore
	@ManyToOne
	private User user;
	
	
	
//	Generating getters and setter of Contacts class fields
	
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

	
	@Override
	public String toString() {
		return "Contacts [contactId=" + contactId + ", name=" + name + ", description=" + description
				+ ", contactEmail=" + contactEmail + ", number=" + number + ", nickname=" + nickname + ", work=" + work
				+ ", img=" + img + ", user=" + user + "]";
	}
	
	
	
	
}
