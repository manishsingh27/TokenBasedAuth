package com.adms.authz.self.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.postgresql.translation.messages_pl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adms.authz.exception.ValidationErrorDTO;
import com.adms.authz.self.user.model.PasswordDto;
import com.adms.authz.self.user.model.Users;
import com.adms.authz.self.user.model.VerificationToken;
import com.adms.authz.self.user.registration.OnRegistrationCompleteEvent;
import com.adms.authz.self.user.service.UsersService;
import com.adms.authz.util.ResponseApi;

@RestController
@RequestMapping("/v1/")
public class UsersController {

	@Autowired
	private UsersService usersService; // Service which will do all data
										// retrieval/manipulation work

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private MessageSource messages;

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender mailSender;

	// -------------------Create a
	// User--------------------------------------------------------

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
			eventPublisher
					.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), getAppUrl()));
			message = new ResponseApi(false,
					"User has been successfully registered with the email provided-:" + user.getEmail(), null);
		}

		// message = new FieldErrorDTO("message", "Error occured during
		// registration with the email provided-:" +user.getEmail());

		return message;
	}

	@RequestMapping(value = "savePassword", method = RequestMethod.POST)
	public ResponseApi savePassword(@Valid @RequestBody PasswordDto passwordDto, HttpServletResponse response,
			HttpSession session) {

		// try{

		final Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		usersService.changeUserPassword(user, passwordDto.getNewPassword());

		// response.sendRedirect(getAppUrl() + "/passwordReset");

		session.invalidate();

		
		ResponseApi message = new ResponseApi(false, getAppUrl()+"/passwordReset", null);
		return message;

		// ResponseApi message = new ResponseApi(false,
		// "Your password has beed changed.", null);
		// return message;
		// return "<html>Your password has beed changed. Please click <a
		// href=\"\\landingPage\"> LogIn </html>";
		// Password reset successfully
		// }/*catch (IOException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }*/

	}

	// ------------------- Update a User
	// --------------------------------------------------------

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "user/{eMailId}", method = RequestMethod.PUT)
	public Users updateUser(@PathVariable String eMailId, @RequestBody Users user, final HttpServletRequest request) {

		if (request.isUserInRole("WRITE_PRIVILEGE")) {
			
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

		return null;

	}

	// -------------------Retrieve All
	// Users--------------------------------------------------------

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public List<Users> listAllUsers(final HttpServletRequest request) {
		
		if (request.isUserInRole("READ_PRIVILEGE")){
			return usersService.findAllUsers();
		}
		return null;
	}

	// -------------------Retrieve Single
	// User--------------------------------------------------------

	@RequestMapping(value = "test/api/user/{eMailId:.+}", method = RequestMethod.GET)
	public Users getTestUser(@PathVariable String eMailId) {
		return usersService.findUserByEmail(eMailId);
	}
	
	@RequestMapping(value = "api/user/{eMailId:.+}", method = RequestMethod.GET)
	public Users getUser(@PathVariable String eMailId) {
		return usersService.findUserByEmail(eMailId);
	}

	// ------------------- Delete a User
	// --------------------------------------------------------

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "user/{eMailId}", method = RequestMethod.DELETE)
	public Users deleteUser(@PathVariable String eMailId, final HttpServletRequest request) {
		
		if (request.isUserInRole("DELETE_PRIVILEGE")) {
			return usersService.deleteUserById(eMailId);
		}
		return null;
	}

	@RequestMapping(value = "api/user/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(final Locale locale, final Model model, @RequestParam("token") final String token)
			throws UnsupportedEncodingException {
		final String result = usersService.validateVerificationToken(token);
		if (result.equals("valid")) {
			final Users user = usersService.getUser(token);
			/*
			 * System.out.println(user); if (user.isUsing2FA()) {
			 * model.addAttribute("qr", usersService.generateQRUrl(user));
			 * return "redirect:/qrcode.html?lang=" + locale.getLanguage(); }
			 */
			// model.addAttribute("message",
			// messages.getMessage("message.accountVerified", null, locale));
			return "<html>Your account has been verified successfully. Please click <a href=\"\\landingPage\"> LogIn </html>";
		}

		// String message = messages.getMessage("auth.message." + result, null,
		// locale);
		String message = "Your account is disabled please check your mail and click on the confirmation link";
		boolean expired = "expired".equals(result);
		return message + "&expired=" + expired + "&token=" + token;
	}

	// Reset password
	@RequestMapping(value = "user/resetPassword", method = RequestMethod.POST)
	public ResponseApi resetPassword(@RequestBody Users userp, final HttpServletRequest request) {
		ResponseApi message = null;
		final Users user = usersService.findUserByEmail(userp.getEmail());
		if (user != null) {
			final String token = UUID.randomUUID().toString();
			usersService.createPasswordResetTokenForUser(user, token);
			mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));

			message = new ResponseApi(false, "You should receive an Password Reset Email shortly", null);
		} else {
			message = new ResponseApi(true, "eMail id is not found.", null);
		}
		return message;
	}

	@RequestMapping(value = "api/user/changePassword", method = RequestMethod.GET)
	public void changePassword(Locale locale, Model model, @RequestParam("id") long id,
			@RequestParam("token") String token, HttpServletResponse response, HttpServletRequest request) {

		try {

			request.getSession().invalidate();
			request.getSession(true);

			String result = usersService.validatePasswordResetToken(id, token);

			if (result != null) {
				// model.addAttribute("message",
				// messages.getMessage("auth.message." + result, null, locale));
				// return "redirect:/login?errorMsg=token is expired/invalid";
				response.sendRedirect("/uaa/login?errorMsg=token is expired/invalid");
			}
			// return "redirect:/updatePassword";

			response.sendRedirect("/uaa/login#/updatePassword");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ============== NON-API Move to Util class============

	private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath,
			final VerificationToken newToken, final Users user) {
		final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
		final String message = "We will send an email with a new registration token to your email account";
		return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	}

	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final String token, final Users user) {
		final String url = contextPath + "/uaa/v1/api/user/changePassword?id=" + user.getId() + "&token=" + token;
		final String message = "Reset Password";
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, Users user) {
		final SimpleMailMessage email = new SimpleMailMessage();
		//todo remove it start
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "15-10-2012 10:20:56";
		Date date = sdf.parse(dateInString);
		email.setSentDate(date);
		}catch(Exception ex){
			
		}
		//End remove it end
		
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setReplyTo(env.getProperty("support.email"));
		email.setFrom(env.getProperty("support.from"));
		return email;
	}

	private String getAppUrl(HttpServletRequest request) {
		return env.getProperty("base.protocol") + request.getServerName() + ":" + request.getServerPort();
	}

	private String getAppUrl() {
		return env.getProperty("base.protocol") + env.getProperty("base.host");
		// return "https://" + request.getServerName() + ":" +
		// request.getServerPort() + request.getContextPath();
	}

}
