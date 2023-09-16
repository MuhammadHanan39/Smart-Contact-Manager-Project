package com.smart.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.smart.manager.entities.User;

public interface ProjectRepo extends JpaRepository<User, Integer>{
	
	@Query(" select u from User u where u.userEmail =:email")
	public User findByUserName(@Param("email") String username);

}
