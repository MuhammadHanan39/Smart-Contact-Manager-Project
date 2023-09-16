package com.smart.manager.configuration;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.manager.entities.User;

public class UserDetailsImpl implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
//	Constructor for initializing the user object from Userdetailservice class
	public UserDetailsImpl(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println(user.getRole());
		SimpleGrantedAuthority simpleGrantedAuthority=	new SimpleGrantedAuthority(user.getRole());  
		
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
