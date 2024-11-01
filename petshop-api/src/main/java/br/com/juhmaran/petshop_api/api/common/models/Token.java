package br.com.juhmaran.petshop_api.api.common.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Token {

    private String authToken;
    private String tokenType;
    private String expiresIn;
    private String username;
    private String name;
    private List<String> roles;

}
