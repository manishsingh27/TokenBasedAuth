package com.adms.authz.self.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adms.authz.exception.ValidationErrorDTO;
import com.adms.authz.self.user.model.Users;

import com.adms.authz.self.user.service.UsersService;
import com.adms.authz.util.ResponseApi;

@RestController
@RequestMapping("/v1/")
public class UsersController {

	@Autowired
	private UsersService usersService; // Service which will do all data retrieval/manipulation work

	// -------------------Create a User--------------------------------------------------------

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public ResponseApi createUser(@Valid @RequestBody Users user) {
		ResponseApi message;

		Users userExists = usersService.findUserByEmail(user.getEmail());

		if (userExists != null) {

			ValidationErrorDTO errorMsg = new ValidationErrorDTO();
			errorMsg.addFieldError("eMailId", 500,
					"There is already a user registered with the email provided-:" + user.getEmail());

			message = new ResponseApi(true, null, errorMsg);
		} else {
			usersService.saveUser(user);
			message = new ResponseApi(false,
					"User has been successfully registered with the email provided-:" + user.getEmail(), null);
		}

		// message = new FieldErrorDTO("message", "Error occured during
		// registration with the email provided-:" +user.getEmail());

		return message;
	}

	// ------------------- Update a User
	// --------------------------------------------------------

	@RequestMapping(value = "user/{eMailId}", method = RequestMethod.PUT)
	public Users updateUser(@PathVariable String eMailId, @RequestBody Users user) {

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

	// -------------------Retrieve All
	// Users--------------------------------------------------------

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public List<Users> listAllUsers() {
		return usersService.findAllUsers();
	}

	// -------------------Retrieve Single
	// User--------------------------------------------------------

	@RequestMapping(value = "api/user/{eMailId:.+}", method = RequestMethod.GET)
	public Users getUser(@PathVariable String eMailId) {
		return usersService.findUserByEmail(eMailId);
	}

	// ------------------- Delete a User
	// --------------------------------------------------------

	@RequestMapping(value = "user/{eMailId}", method = RequestMethod.DELETE)
	public Users deleteUser(@PathVariable String eMailId) {
		return usersService.deleteUserById(eMailId);
	}

}
