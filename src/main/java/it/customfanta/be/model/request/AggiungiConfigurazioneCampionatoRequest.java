package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class AggiungiConfigurazioneCampionatoRequest {

    private String chiaveCampionato;
    private String chiaveConfigurazione;
    private String valoreConfigurazione;

}
