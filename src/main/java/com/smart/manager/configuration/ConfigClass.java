package com.smart.manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigClass {


    @Bean
    UserDetailsService userDetailsService() {
    	System.out.println("third Step");
        return new UserDetailsServiceImpl();
    }	
	
    
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
    	System.out.println("First Step");
    	return new BCryptPasswordEncoder();
    	
    }
	
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
    	System.out.println("Second Step");
    	DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
    	
    	dao.setPasswordEncoder(passwordEncoder());
    	dao.setUserDetailsService(userDetailsService());
    	
    	return dao;	
    	
    }
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
    	
    	http.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
    	.requestMatchers("/admin/**").hasRole("admin")
    	.anyRequest().permitAll().and().formLogin().loginPage("/login").defaultSuccessUrl("/user/dashboard").loginProcessingUrl("/signin")
    	.and().csrf().disable();
    	
    	return http.build();
    	
    }
    
    
   
    
    @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception  {
	   System.out.print("Last Step");
	   return authenticationConfiguration.getAuthenticationManager();
	   
	   
   }
	

}
