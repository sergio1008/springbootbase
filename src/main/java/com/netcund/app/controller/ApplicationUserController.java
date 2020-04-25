package com.netcund.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netcund.app.dto.ApplicationUserDTO;
import com.netcund.app.services.ApplicationUserService;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

	@Autowired
	private ApplicationUserService service;
	
    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUserDTO user) {
    	this.service.signUp(user);
    }
    
    @PostMapping("/sign-in")
    public void signIn(@RequestBody ApplicationUserDTO user) {
    	this.service.signUp(user);
    }    
}
