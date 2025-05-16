package com.dripg.drip_shop.auth.repositories;

import com.dripg.drip_shop.auth.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Authority findByRoleCode(String user);
}
