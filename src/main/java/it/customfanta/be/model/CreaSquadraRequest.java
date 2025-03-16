package it.customfanta.be.model;

import lombok.Data;

import java.util.List;

@Data
public class CreaSquadraRequest {

    private Squadra squadra;
    private List<String> chiaviPersonaggi;


}
