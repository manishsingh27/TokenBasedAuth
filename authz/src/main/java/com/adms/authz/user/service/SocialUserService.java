package com.adms.authz.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.security.SocialUserDetailsService;

import com.adms.authz.user.repo.User;

public interface SocialUserService extends SocialUserDetailsService, UserDetailsService{

    User loadUserByConnectionKey(ConnectionKey connectionKey);
    User loadUserByUserId(String userId);
    User loadUserByUsername(String username);
    void updateUserDetails(User user);

}
