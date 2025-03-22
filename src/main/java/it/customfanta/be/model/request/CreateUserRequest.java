package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;

    private String nome;

    private String mail;
    private String password;

}
