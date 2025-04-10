package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "utenti")
@Data
public class Utente {

    @Id
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
