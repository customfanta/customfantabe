package it.customfanta.be.model;

import lombok.Data;


@Data
public class Azione {

    private String chiave;


    private String azione;
    private String descrizione;
    private Integer punteggio;
    private String chiaveCampionato;

}
