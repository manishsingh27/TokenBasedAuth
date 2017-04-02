package com.adms.authz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import com.adms.authz.social.connection.SimpleUsersConnectionRepository;
import com.adms.authz.user.UserAuthenticationUserIdSource;
import com.adms.authz.user.service.SocialUserService;

@Configuration
@EnableSocial
public class SocialConfiguration extends SocialConfigurerAdapter {


	@Autowired
	private ConnectionSignUp autoSignUpHandler;

	@Autowired
	private SocialUserService userService;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		cfConfig.addConnectionFactory(new FacebookConnectionFactory(
				env.getProperty("facebook.appKey"),
				env.getProperty("facebook.appSecret")));
	}

	@Override
	public UserIdSource getUserIdSource() {
		// retrieve the UserId from the UserAuthentication in the security context
		return new UserAuthenticationUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		/*SimpleUsersConnectionRepository usersConnectionRepository =
				new SimpleUsersConnectionRepository(userService, connectionFactoryLocator);
		*/
		
		InMemoryUsersConnectionRepository usersConnectionRepository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
		// if no local user record exists yet for a facebook's user id
		// automatically create a User and add it to the database
		usersConnectionRepository.setConnectionSignUp(autoSignUpHandler);

		return usersConnectionRepository;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}


}
