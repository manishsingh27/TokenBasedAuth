package com.adms.authz.user;

import org.springframework.social.security.SocialUserDetails;

import com.adms.authz.user.repo.User;

public class SocialUser extends User {

	//private String id;
	private String first_name;
	private String last_name;
	private String email;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
