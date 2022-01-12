package com.serbernal.basespring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serbernal.basespring.dto.UserDTO;
import com.serbernal.basespring.security.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

	@Autowired
	private UserService service;
	
	@PostMapping("/signup")
	public ResponseEntity<UserDTO> newUser(@RequestBody UserDTO dto){
		return new ResponseEntity<>(this.service.saveUser(dto), HttpStatus.OK);	
	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> login(
			@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password){
		String token = this.service.getToken(username, password);
		if(token != null) {
			return new ResponseEntity<>(token , HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	} 
}
