package it.customfanta.be.model;

import lombok.Data;

@Data
public class FindUserRequest {

    private String username;
    private String mail;

}
