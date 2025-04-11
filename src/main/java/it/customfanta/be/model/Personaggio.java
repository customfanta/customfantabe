package it.customfanta.be.model;

import lombok.Data;


@Data
public class Personaggio {

    private String chiave;

    private String nominativo;
    private String descrizione;
    private Integer costo;
    private String chiaveCampionato;

}
