package it.customfanta.be.model;

import it.customfanta.be.model.request.SquadraRequest;
import lombok.Data;

import java.util.List;

@Data
public class CreaSquadraRequest {

    private SquadraRequest squadra;
    private List<String> chiaviPersonaggi;


}
