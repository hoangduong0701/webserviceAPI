package com.dripg.drip_shop.controllers;

import com.dripg.drip_shop.dto.AddressRequest;
import com.dripg.drip_shop.entities.Address;
import com.dripg.drip_shop.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressRequest addressRequest, Principal principal) {
        System.out.println("Raw addressRequest: " + addressRequest);
        System.out.println("isDefault value: " + addressRequest.isDefault());
        Address address = addressService.createAddress(addressRequest, principal);
        return new ResponseEntity<>(address, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable UUID id){
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/default")
    public ResponseEntity<Address> getDefaultAddress(Principal principal) {
        Address defaultAddress = addressService.getDefaultAddress(principal);
        if (defaultAddress != null) {
            return new ResponseEntity<>(defaultAddress, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
