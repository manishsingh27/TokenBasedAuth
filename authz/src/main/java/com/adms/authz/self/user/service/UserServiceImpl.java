package com.adms.authz.self.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adms.authz.self.user.model.PasswordResetToken;
import com.adms.authz.self.user.model.Role;
import com.adms.authz.self.user.model.Users;
import com.adms.authz.self.user.model.VerificationToken;
import com.adms.authz.self.user.repository.PasswordResetTokenRepository;
import com.adms.authz.self.user.repository.RoleRepository;
import com.adms.authz.self.user.repository.UsersRepository;
import com.adms.authz.self.user.repository.VerificationTokenRepository;

@Service("usersService")
public class UserServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	@Override
	public Users findUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	@Override
	public Users saveUser(Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(false);

		// Role userReaderRole = roleRepository.findByRole("ROLE_READER");
		// Role userWriterRole = roleRepository.findByRole("ROLE_WRITER");

		Role userRole = roleRepository.findByRole("ROLE_USER");
		/*
		 * List <Role> roleList = new ArrayList<Role>();
		 * roleList.addAll(Arrays.asList(userReaderRole));
		 * roleList.addAll(Arrays.asList(userWriterRole)); user.setRoles(new
		 * HashSet<Role>(roleList));
		 */

		// userRole = roleRepository.findByRole("ROLE_WRITER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

		return usersRepository.save(user);
	}

	@Override
	public void createVerificationTokenForUser(final Users user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final Users user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			tokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}

		user.setActive(true);
		// tokenRepository.delete(verificationToken);
		usersRepository.save(user);
		return TOKEN_VALID;
	}

	@Override
	public Users getUser(final String verificationToken) {
		final VerificationToken token = tokenRepository.findByToken(verificationToken);
		if (token != null) {
			return token.getUser();
		}
		return null;
	}

	@Override
	public List<Users> findAllUsers() {

		return populateDummyUsers();
		// return usersRepository.findAllUsers();
	}

	@Override
	public Users updateUser(Users user) {

		return usersRepository.save(user);
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
	public void createPasswordResetTokenForUser(final Users user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	@Override
	public Users deleteUserById(String eMailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeUserPassword(final Users user, final String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		usersRepository.save(user);
	}

	@Override
	public String validatePasswordResetToken(long id, String token) {
		final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
		if ((passToken == null) || (passToken.getUser().getId() != id)) {
			return "invalidToken";
		}

		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return "expired";
		}

		final Users user = passToken.getUser();
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
				Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}

}
