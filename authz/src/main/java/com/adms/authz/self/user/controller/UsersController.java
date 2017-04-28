package com.adms.authz.self.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adms.authz.exception.ValidationErrorDTO;
import com.adms.authz.self.user.model.Users;
import com.adms.authz.self.user.registration.OnRegistrationCompleteEvent;
import com.adms.authz.self.user.service.UsersService;
import com.adms.authz.util.ResponseApi;

@RestController
@RequestMapping("/v1/")
public class UsersController {

	@Autowired
	private UsersService usersService; // Service which will do all data retrieval/manipulation work
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageSource messages;
	
    @Autowired
    private Environment env;

	// -------------------Create a User--------------------------------------------------------

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public ResponseApi createUser(@Valid @RequestBody Users user, final HttpServletRequest request) {
		ResponseApi message;

		Users userExists = usersService.findUserByEmail(user.getEmail());

		if (userExists != null) {

			ValidationErrorDTO errorMsg = new ValidationErrorDTO();
			errorMsg.addFieldError("eMailId", 500,
					"There is already a user registered with the email provided-:" + user.getEmail());

			message = new ResponseApi(true, null, errorMsg);
		} else {
			Users registeredUser = usersService.saveUser(user);
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), getAppUrl(request)));
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
	
	@RequestMapping(value = "api/user/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(final Locale locale, final Model model, @RequestParam("token") final String token)
			throws UnsupportedEncodingException {
		final String result = usersService.validateVerificationToken(token);
		if (result.equals("valid")) {
			final Users user = usersService.getUser(token);
			/*System.out.println(user);
			if (user.isUsing2FA()) {
				model.addAttribute("qr", usersService.generateQRUrl(user));
				return "redirect:/qrcode.html?lang=" + locale.getLanguage();
			}*/
			//model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
			return "<html>Your account has been verified successfully. Please click <a href=\"\\landingPage\"> LogIn </html>";
		}

		//String message = messages.getMessage("auth.message." + result, null, locale);
		String message = "Your account is disabled please check your mail and click on the confirmation link";
		boolean expired = "expired".equals(result);
		return message + "&expired=" + expired + "&token=" + token ;
	}
	
	private String getAppUrl(HttpServletRequest request) {
		return env.getProperty("base.protocol") + env.getProperty("base.host");
        //return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
