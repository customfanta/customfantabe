package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "squadre")
@Data
public class Squadra {

    @Id
    private String chiave;

    private String nome;
    private String descrizione;
    private String usernameUser;

}
