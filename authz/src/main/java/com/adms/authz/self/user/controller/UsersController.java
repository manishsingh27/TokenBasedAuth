package com.adms.authz.self.user.controller;



import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adms.authz.exception.FieldErrorDTO;
import com.adms.authz.self.user.model.Users;

import com.adms.authz.self.user.service.UsersService;


@RestController
@RequestMapping("/v1/")
public class UsersController {
	
	@Autowired
	private UsersService usersService; //Service which will do all data retrieval/manipulation work
	
	
	//-------------------Create a User--------------------------------------------------------
	
	@RequestMapping(value = "user", method=RequestMethod.POST)
	public FieldErrorDTO createUser (@Valid @RequestBody Users user){	
		FieldErrorDTO message;
		
		try{
		Users userExists = usersService.findUserByEmail(user.getEmail());
		System.out.println("Creating User " + user.getName() + "userExists-" + userExists);
		 
		if (userExists != null) {
			System.out.println("userExists User " + userExists.getName());
			//message="There is already a user registered with the email provided-:" +user.getEmail() ;
			message = new FieldErrorDTO("message", "There is already a user registered with the email provided-:" +user.getEmail());
		}else{
			System.out.print("Newuser");
			//message="User has been registered with the email provided-:" +user.getEmail() ;
			usersService.saveUser(user);
			message = new FieldErrorDTO("message", "User has been successfully registered with the email provided-:" + user.getEmail());
		}
		} catch(Exception ex){
			System.out.print("Error is-" + ex.getMessage());
			//message = "Error occured during registration";
			message = new FieldErrorDTO("message", "Error occured during registration with the email provided-:" +user.getEmail());
		}
					
		return message;
	}
	
	//------------------- Update a User --------------------------------------------------------
	
		@RequestMapping (value="user/{eMailId}", method = RequestMethod.PUT)
		public Users updateUser (@PathVariable String eMailId, @RequestBody Users user){
			
			Users currentUser = usersService.findUserByEmail(eMailId);
			
			currentUser.setName(user.getName());        
	        currentUser.setLastName(user.getLastName());
	        currentUser.setDob(user.getDob());
	        currentUser.setGrade(user.getGrade());
	        currentUser.setGender(user.getGender());
	        currentUser.setMobileNumber(user.getMobileNumber());
	        currentUser.setCity(user.getCity());
	        currentUser.setState(user.getState());
	        currentUser.setCountry(user.getCountry());
	        
			return usersService.updateUser(user);
					
		}
	
	
	//-------------------Retrieve All Users--------------------------------------------------------
	
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public List<Users> listAllUsers(){
		return usersService.findAllUsers();
	}
					
	
	//-------------------Retrieve Single User--------------------------------------------------------
	
	@RequestMapping(value = "api/user/{eMailId:.+}", method = RequestMethod.GET)
	public Users getUser(@PathVariable String eMailId){	
		return usersService.findUserByEmail(eMailId);
	}
	
	
	
	//------------------- Delete a User --------------------------------------------------------
	
	@RequestMapping(value = "user/{eMailId}", method=RequestMethod.DELETE)
	public Users deleteUser (@PathVariable String eMailId){	
		return usersService.deleteUserById(eMailId);
	}
	
}
