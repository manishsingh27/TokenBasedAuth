package com.adms.stdedu.user.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adms.stdedu.user.model.User;

@RestController
@RequestMapping("/v1/")
public class UserController {
	
	@RequestMapping(value = "user", method=RequestMethod.GET)
	public User create (Principal user, @RequestHeader("Authorization") String authTokens){	
		//return "{userId:" + (String) user.getName()+ "}";
		User userObj = new User();
		userObj.setEmail((String) user.getName());
		return userObj;
	}

}
