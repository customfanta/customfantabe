package it.customfanta.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "campionati")
@Data
public class Campionato {

    @Id
    private String chiave;

    private String nome;
    private String descrizione;
    private String usernameUtenteOwner;

}
