package com.dripg.drip_shop.auth.services;

import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.auth.repositories.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2Service {

    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    private AuthorityService authorityService;
    public User getUser(String username) {
        return userDetailRepository.findByEmail(username);
    }

    public User createUser(OAuth2User oAuth2User, String provider) {
        String fistName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        String email = oAuth2User.getAttribute("email");
        User user =  User.builder()
                .firstName(fistName)
                .lastName(lastName)
                .email(email)
                .provider(provider)
                .enabled(true)
                .authorities(authorityService.getUserAuthority())
                .build();
        return userDetailRepository.save(user);
    }
}
