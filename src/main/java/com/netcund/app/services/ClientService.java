package com.netcund.app.services;

import org.springframework.stereotype.Service;

import com.netcund.app.dto.ClientDTO;

@Service
public interface ClientService {
	
	public ClientDTO saveClient(ClientDTO dto);
}
