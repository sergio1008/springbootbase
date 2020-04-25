package com.netcund.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netcund.app.entities.ApplicationUserEntity;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Long> {
	ApplicationUserEntity findByLogin(String username);
}
