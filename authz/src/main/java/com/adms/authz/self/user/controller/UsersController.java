package com.adms.authz.self.user.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adms.authz.self.user.model.Users;

import com.adms.authz.self.user.service.UsersService;


@RestController
@RequestMapping("/v1/")
public class UsersController {
	
	@Autowired
	private UsersService usersService; //Service which will do all data retrieval/manipulation work
	
	
	//-------------------Create a User--------------------------------------------------------
	
	@RequestMapping(value = "user", method=RequestMethod.POST)
	public String createUser (@Valid @RequestBody Users user){	
		String message="";
		
		try{
		Users userExists = usersService.findUserByEmail(user.getEmail());
		System.out.println("Creating User " + user.getName() + "userExists-" + userExists);
		 
		if (userExists != null) {
			System.out.println("userExists User " + userExists.getName());
			message="There is already a user registered with the email provided-:" +user.getEmail() ;
		}else{
			System.out.print("Newuser");
			message="User has been registered with the email provided-:" +user.getEmail() ;
			usersService.saveUser(user);
		}
		} catch(Exception ex){
			System.out.print("Error is-" + ex.getMessage());
			message = "Error occured during registration";
		}
					
		return message;
	}
	
	
	//-------------------Retrieve All Users--------------------------------------------------------
	
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public List<Users> listAllUsers(){
		return usersService.findAllUsers();
	}
	
	
	//-------------------Retrieve Single User--------------------------------------------------------
	
	@RequestMapping(value = "user/{eMailId:.+}", method = RequestMethod.GET)
	public Users getUser(@PathVariable String eMailId){	
		return usersService.findUserByEmail(eMailId);
	}
	
	
	//------------------- Update a User --------------------------------------------------------
	
	@RequestMapping (value="user/{eMailId}", method = RequestMethod.PUT)
	public Users updateUser (@PathVariable String eMailId, @RequestBody Users user){
		
		Users currentUser = usersService.findUserByEmail(eMailId);
		
		currentUser.setName(user.getName());        
        currentUser.setEmail(user.getEmail());
        
		return usersService.updateUser(user);
	}
	
	
	//------------------- Delete a User --------------------------------------------------------
	
	@RequestMapping(value = "user/{eMailId}", method=RequestMethod.DELETE)
	public Users deleteUser (@PathVariable String eMailId){	
		return usersService.deleteUserById(eMailId);
	}
	
}
