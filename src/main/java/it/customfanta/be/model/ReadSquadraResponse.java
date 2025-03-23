package it.customfanta.be.model;

import lombok.Data;

import java.util.List;

@Data
public class ReadSquadraResponse {

    private String ownerSquadra;
    private Squadra squadra;
    private List<PersonaggioResponse> personaggi;
    private Integer punteggioSquadra;

}
