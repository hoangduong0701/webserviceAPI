package com.dripg.drip_shop.repositories;

import com.dripg.drip_shop.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Modifying
    @Query("UPDATE Address a SET a.defaultAddress = :defaultAddress WHERE a.user.id = :userId")
    void updateAllDefaultAddresses(@Param("userId") UUID userId, @Param("defaultAddress") boolean defaultAddress);
    Address findByUserIdAndDefaultAddressTrue(UUID userId);
}
