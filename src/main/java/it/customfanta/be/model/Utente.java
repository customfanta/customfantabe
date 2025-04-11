package it.customfanta.be.model;

import lombok.Data;


@Data
public class Utente {

    private String username;
    private String username_lower;

    private String nome;
    private String nome_lower;

    private String mail;
    private String mail_lower;
    private String password;

    private Boolean mailCertificata;
    private String uuidMailCertificazione;

}
