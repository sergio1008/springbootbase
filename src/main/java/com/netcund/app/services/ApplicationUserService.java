package com.netcund.app.services;

import org.springframework.stereotype.Service;

import com.netcund.app.dto.ApplicationUserDTO;

@Service
public interface ApplicationUserService {
	
    void signUp( ApplicationUserDTO user);
}
