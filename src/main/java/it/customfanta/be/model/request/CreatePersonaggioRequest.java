package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class CreatePersonaggioRequest {

    private String nominativo;
    private String descrizione;
    private Integer costo;
    private String chiaveCampionato;

}
