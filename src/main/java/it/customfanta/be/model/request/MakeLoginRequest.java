package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class MakeLoginRequest {

    private String usernameMail;
    private String password;

}
