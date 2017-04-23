package com.adms.authz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.adms.authz.social.filter.StatelessAuthenticationFilter;
import com.adms.authz.social.handler.SocialAuthenticationSuccessHandler;
import com.adms.authz.user.service.SocialUserService;

@Configuration
@Order(-20)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//@Autowired
	//private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler;

	@Autowired
	private StatelessAuthenticationFilter statelessAuthenticationFilter;

	@Autowired
	private UserIdSource userIdSource;

	@Autowired
	private SocialUserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Set a custom successHandler on the SocialAuthenticationFilter
				final SpringSocialConfigurer socialConfigurer = new SpringSocialConfigurer();
				socialConfigurer.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
					@Override
					public <O extends SocialAuthenticationFilter> O postProcess(O socialAuthenticationFilter) {
						socialAuthenticationFilter.setAuthenticationSuccessHandler(socialAuthenticationSuccessHandler);
						return socialAuthenticationFilter;
					}
				});
				
		// @formatter:off 
		http
			.formLogin().loginPage("/login").permitAll()
		.and()
			.requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/auth/**", "/v1/user", "/view/register.html", "/view/headers.html", 
					"/view/login.html","/view/regHeaders.html")
		.and()
			.authorizeRequests()
			.antMatchers("/view/register.html", "/v1/user", "/view/headers.html", "/view/login.html", "/view/regHeaders.html").permitAll()
			.anyRequest().authenticated().and()
			.csrf().disable()
			// add custom authentication filter for complete stateless JWT based authentication
			//.addFilterBefore(statelessAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class)

			// apply the configuration from the socialConfigurer (adds the SocialAuthenticationFilter)
			.apply(socialConfigurer.userIdSource(userIdSource));
		// @formatter:on
	}
	
	/*@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.parentAuthenticationManager(authenticationManager);	
		/*auth.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);*/
		
		auth.
		jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
		
		/*auth.inMemoryAuthentication()
		.withUser("reader")
		.password("reader")
		.authorities("ROLE_READER")
		.and()
		.withUser("writer")
		.password("writer")
		.authorities("ROLE_READER", "ROLE_WRITER")
		.and()
		.withUser("guest")
		.password("guest")
		.authorities("ROLE_GUEST");*/
	}

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.parentAuthenticationManager(authenticationManager);
	}*/
	


}
