package com.netcund.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netcund.app.entities.ApplicationRoleEntity;

@Repository
public interface ApplicationRoleRepository extends JpaRepository<ApplicationRoleEntity, Long> {

}
