package com.adms.authz.self.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adms.authz.self.user.model.Users;


@Repository("usersRepository")
public interface UsersRepository extends JpaRepository<Users, Long> {
	 Users findByEmail(String email);
	 //List<Users> findAllUsers();
}

