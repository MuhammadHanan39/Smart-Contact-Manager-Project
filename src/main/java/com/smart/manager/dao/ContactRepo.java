package com.smart.manager.dao;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.smart.manager.entities.Contacts;

import jakarta.transaction.Transactional;

public interface ContactRepo extends PagingAndSortingRepository<Contacts, Integer>,JpaRepository<Contacts, Integer> {
	
	@Query(value="from Contacts as c where c.user.userId =:uId")
	public Page<Contacts> findAllContacts(@Param("uId") Integer userId, Pageable pageable);
	
	@Query(value="select * from contacts where contact_id =:cId",nativeQuery = true)
	public Contacts getContact(@Param("cId") int cId);
	
	@Modifying
	@Transactional
	@Query("delete from Contacts c where c.Id =:id")
	public void deleteContact(@Param("id") Integer id);

}
