package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class CreateAzioneRequest {
    private String azione;
    private String descrizione;
    private Integer punteggio;
    private String chiaveCampionato;
}
