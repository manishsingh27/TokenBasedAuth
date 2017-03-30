package com.adms.authzapi.home.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class HomeController {
	
	@RequestMapping(value = "user", method=RequestMethod.GET)
	public String create (Principal user, @RequestHeader("Authorization") String authTokens){	
		return "Test:-" + (String) user.getName() + "-" + authTokens;
	}

}
