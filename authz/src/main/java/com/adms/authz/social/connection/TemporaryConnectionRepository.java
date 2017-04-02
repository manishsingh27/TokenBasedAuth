package com.adms.authz.social.connection;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;

public class TemporaryConnectionRepository extends InMemoryUsersConnectionRepository {

    public TemporaryConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        super(connectionFactoryLocator);
    }

}
