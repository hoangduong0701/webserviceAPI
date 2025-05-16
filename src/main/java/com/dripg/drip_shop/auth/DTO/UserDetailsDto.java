package com.dripg.drip_shop.auth.DTO;

import com.dripg.drip_shop.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDto {
    private UUID id;
    private String fistName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Object authorityList;
    private List<Address> addressList;
}
