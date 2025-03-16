package it.customfanta.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "utenti_campionati")
@Data
public class UtenteCampionato {

    @Id
    private String chiave;

    private String chiaveCampionato;
    private String usernameUtente;
    private String ruoloUtente;

}
