package it.customfanta.be.model;

import lombok.Data;

@Data
public class InvitoCampionatoResponse {

    private String chiave;
    private String ruoloInvito;
    private Campionato campionato;
    private String usernameUtenteInvitato;
    private String usernameUtenteCheHaInvitato;

}
