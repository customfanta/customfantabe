package it.customfanta.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "azioni")
@Data
public class Azione {

    @Id
    private String azione;

    private String descrizione;
    private Integer punteggio;

}
