package com.dripg.drip_shop.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private String name;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    @JsonProperty("isDefault")
    private boolean defaultAddress;
    public boolean isDefault() {
        return defaultAddress;
    }

    // Custom setter để phù hợp với request từ frontend
    public void setIsDefault(boolean value) {
        this.defaultAddress = value;
    }
}
