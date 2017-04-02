package com.adms.authz.social.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.adms.authz.user.SocialUser;
import com.adms.authz.user.UserRole;
import com.adms.authz.user.repo.User;
import com.adms.authz.user.repo.UserRepository;

@Component
public class AutoSignUpHandler implements ConnectionSignUp {


    @Autowired
    UserRepository userRepository;

    private volatile long userCount;

    @Override
    @Transactional
    public String execute(final Connection<?> connection) {
        //add new users to the db with its default roles for later use in SocialAuthenticationSuccessHandler
        final User user = new User();
       // user.setUsername(generateUniqueUserName(connection.fetchUserProfile().getFirstName()));
        Facebook facebook = (Facebook) connection.getApi();
        String [] fields = { "id", "email",  "first_name", "last_name" };
       // { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"}

        SocialUser userProfile = (SocialUser) facebook.fetchObject("me", SocialUser.class, fields);
        user.setUsername(generateUniqueUserName(userProfile.getFirst_name()));
        
        user.setProviderId(connection.getKey().getProviderId());
        user.setProviderUserId(connection.getKey().getProviderUserId());
        user.setAccessToken(connection.createData().getAccessToken());
        grantRoles(user);
        userRepository.save(user);
        return user.getUserId();
    }

    private void grantRoles(final User user) {
        user.grantRole(UserRole.USER);

        //grant admin rights to the first user
        if (userCount == 0) {
            userCount = userRepository.count();
            if (userCount == 0) {
                user.grantRole(UserRole.ADMIN);
            }
        }
    }

    private String generateUniqueUserName(final String firstName) {
        String username = getUsernameFromFirstName(firstName);
        String option = username;
        for (int i = 0; userRepository.findByUsername(option) != null; i++) {
            option = username + i;
        }
        return option;
    }

    private String getUsernameFromFirstName(final String userId) {
        final int max = 25;
        int index = userId.indexOf(' ');
        index = index == -1 || index > max ? userId.length() : index;
        index = index > max ? max : index;
        return userId.substring(0, index);
    }


}
