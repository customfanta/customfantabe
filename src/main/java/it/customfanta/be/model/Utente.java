package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "utenti")
@Data
public class Utente {

    @Id
    private String username;

    private String nome;

    private String mail;
    private String password;

}
