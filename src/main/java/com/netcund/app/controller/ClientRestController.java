package com.netcund.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netcund.app.dto.ClientDTO;
import com.netcund.app.services.ClientService;

@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/clients")
public class ClientRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientRestController.class);
	
	@Autowired
	private ClientService service;

	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	public ResponseEntity<ClientDTO> saveClient(){
		ClientDTO response = new ClientDTO();
		
		return new ResponseEntity<ClientDTO>(response, HttpStatus.OK);
	}
}
