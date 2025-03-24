package it.customfanta.be.model;

import lombok.Data;

import java.util.List;

@Data
public class ReadSquadraResponse {

    private boolean laMiaSquadra;
    private Squadra squadra;
    private List<PersonaggioResponse> personaggi;
    private Integer punteggioSquadra;
    private Integer posizioneClassifica;

}
