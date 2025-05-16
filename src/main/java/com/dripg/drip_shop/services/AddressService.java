package com.dripg.drip_shop.services;

import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.dto.AddressRequest;
import com.dripg.drip_shop.entities.Address;
import com.dripg.drip_shop.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;
//ok
@Service
public class AddressService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AddressRepository addressRepository;
    @Transactional
    public Address createAddress(AddressRequest addressRequest, Principal principal){
        User user= (User) userDetailsService.loadUserByUsername(principal.getName());

        if (addressRequest.isDefault()) {
            addressRepository.updateAllDefaultAddresses(user.getId(), false);
        }
        Address address = Address.builder()
                .name(addressRequest.getName())
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .zipCode(addressRequest.getZipCode())
                .phoneNumber(addressRequest.getPhoneNumber())
                .defaultAddress(addressRequest.isDefault())
                .user(user)
                .build();
        return addressRepository.save(address);
    }

    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }
    public Address getDefaultAddress(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        return addressRepository.findByUserIdAndDefaultAddressTrue(user.getId());
    }
}
