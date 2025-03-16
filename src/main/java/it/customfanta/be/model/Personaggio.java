package it.customfanta.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "personaggi")
@Data
public class Personaggio {

    @Id
    private String chiave;

    private String nominativo;
    private String descrizione;
    private Integer costo;
    private String chiaveCampionato;

}
