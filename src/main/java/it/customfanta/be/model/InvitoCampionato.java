package it.customfanta.be.model;

import lombok.Data;


@Data
public class InvitoCampionato {

    private String chiave;

    private String usernameUtenteInvitato;
    private String ruoloInvito;
    private String chiaveCampionato;
    private String usernameUtenteCheHaInvitato;

}
