package com.dripg.drip_shop.auth.services;

import com.dripg.drip_shop.auth.entities.Authority;
import com.dripg.drip_shop.auth.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public List<Authority> getUserAuthority() {
        List<Authority> authorityList = new ArrayList<>();
        Authority authority = authorityRepository.findByRoleCode("USER");
        authorityList.add(authority);
        return authorityList;
    }

    public Authority createAuthority(String role, String description) {
        Authority authority = Authority.builder().roleCode(role).roleDescription(description).build();
        return authorityRepository.save(authority);
    }
}
