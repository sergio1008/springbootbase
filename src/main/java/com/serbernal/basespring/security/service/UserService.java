package com.serbernal.basespring.security.service;

import org.springframework.stereotype.Service;

import com.serbernal.basespring.dto.UserDTO;

@Service
public interface UserService {

	public UserDTO saveUser(UserDTO user);
	
	public String getToken(String username, String password);
	
}
