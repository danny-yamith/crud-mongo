package com.example.springmongo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

    private String address1;
    private String address2;
    private String city;
}
