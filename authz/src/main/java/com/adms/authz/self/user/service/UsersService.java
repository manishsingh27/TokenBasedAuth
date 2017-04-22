package com.adms.authz.self.user.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.adms.authz.self.user.model.Users;

public interface UsersService {
	public Users findUserByEmail(String email);
	
	public void saveUser(Users user);
	
	public List<Users> findAllUsers();
	
	Users updateUser(Users user);
	
	Users deleteUserById(String eMailId);
	
	UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
}
