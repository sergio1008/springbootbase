package com.netcund.app.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.netcund.app.dto.ApplicationUserDTO;
import com.netcund.app.entities.ApplicationRoleEntity;
import com.netcund.app.entities.ApplicationUserEntity;
import com.netcund.app.repositories.ApplicationRoleRepository;
import com.netcund.app.repositories.ApplicationUserRepository;
import com.netcund.app.services.ApplicationUserService;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
	@Autowired
	public ApplicationUserRepository userRepo;
	
	@Autowired
	public ApplicationRoleRepository roleRepo;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void signUp(ApplicationUserDTO user) {
		ApplicationUserEntity entity = new ApplicationUserEntity();
		entity.setLogin(user.getLogin());
		entity.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		entity.setName(user.getName());
		entity.setLastName(user.getLastName());
		entity.setEmail(user.getEmail());
		entity.setPhone(user.getPhone());
		List<ApplicationRoleEntity> roles = new ArrayList<>();
		Optional<ApplicationRoleEntity> role = this.roleRepo.findById(2L);
		roles.add( role.isPresent() ? role.get() : null);
		entity.setRoles(roles);
		this.userRepo.save(entity);
	}

}
