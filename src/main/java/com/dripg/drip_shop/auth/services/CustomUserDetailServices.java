package com.dripg.drip_shop.auth.services;

import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.auth.repositories.UserDetailRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServices implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userDetailRepository.findByEmail(username);
        if (null == user) {
            throw new UsernameNotFoundException(username);

        }

        return user;
    }
}
