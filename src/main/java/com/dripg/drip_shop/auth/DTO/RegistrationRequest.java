package com.dripg.drip_shop.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {
    private String fistName;
    private String lastName;
    private String email;
    private CharSequence password;
    private String phoneNumber;
}
