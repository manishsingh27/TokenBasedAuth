package com.adms.authz.self.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adms.authz.self.user.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

	@Override
	void delete(Privilege privilege);

}