package com.netcund.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netcund.app.entities.ClientEntity;

@Repository
public interface ClientRepository  extends JpaRepository<ClientEntity, Long> {

}
