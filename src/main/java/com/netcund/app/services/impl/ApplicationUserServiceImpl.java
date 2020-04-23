package com.netcund.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.netcund.app.dto.ApplicationUserDTO;
import com.netcund.app.entities.ApplicationUserEntity;
import com.netcund.app.repositories.ApplicationUserRepository;
import com.netcund.app.services.ApplicationUserService;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
	@Autowired
	public ApplicationUserRepository repo;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void signUp(ApplicationUserDTO user) {
		ApplicationUserEntity entity = new ApplicationUserEntity();
		entity.setName(user.getUsername());
		entity.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		this.repo.save(entity);
	}
}
