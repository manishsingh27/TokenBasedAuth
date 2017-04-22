package com.adms.authz.self.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adms.authz.self.user.model.Role;
import com.adms.authz.self.user.model.Users;
import com.adms.authz.self.user.repository.RoleRepository;
import com.adms.authz.self.user.repository.UsersRepository;

@Service("usersService")
public class UserServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Users findUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	@Override
	public void saveUser(Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		Role userRole = roleRepository.findByRole("role_reader");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		usersRepository.save(user);
	}

	@Override

	public List<Users> findAllUsers() {

		return populateDummyUsers();
		// return usersRepository.findAllUsers();
	}

	public Users updateUser(Users user) {
		return null;
		// int index = users.indexOf(user);
		// users.set(index, user);
	}

	@Override
	// @Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users user = usersRepository.findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isActive(), true, true, true, authorities);
	}

	private static List<Users> populateDummyUsers() {
		List<Users> users = new ArrayList<Users>();
		Users user = new Users();
		user.setId(1);
		user.setEmail("sam@abc.com");
		user.setName("Sam");
		users.add(user);
		user.setId(2);
		user.setEmail("tomy@abc.com");
		user.setName("Tomy");
		users.add(user);

		return users;
	}

	@Override
	public Users deleteUserById(String eMailId) {
		// TODO Auto-generated method stub
		return null;
	}

}
