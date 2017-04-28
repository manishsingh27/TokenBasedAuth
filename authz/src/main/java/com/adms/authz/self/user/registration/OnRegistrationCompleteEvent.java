package com.adms.authz.self.user.registration;


import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.adms.authz.self.user.model.Users;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Users user;

    public OnRegistrationCompleteEvent(final Users user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Users getUser() {
        return user;
    }

}