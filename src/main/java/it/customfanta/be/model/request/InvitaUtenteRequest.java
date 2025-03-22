package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class InvitaUtenteRequest {

    private String usernameUtenteInvitato;
    private String ruoloInvito;
    private String chiaveCampionato;

}
