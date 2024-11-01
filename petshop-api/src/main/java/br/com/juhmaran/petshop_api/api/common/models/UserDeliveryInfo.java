package br.com.juhmaran.petshop_api.api.common.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeliveryInfo {

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private Address address;

}
