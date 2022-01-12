package com.serbernal.basespring.security.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serbernal.basespring.security.persistence.entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{
 
	public User findByUsernameAndPassword(String username, String password);
}
