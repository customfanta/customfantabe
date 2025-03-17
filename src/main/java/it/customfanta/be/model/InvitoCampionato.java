package it.customfanta.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "squadre")
@Data
public class InvitoCampionato {

    @Id
    private String chiave;

    private String usernameUtenteInvitato;
    private String ruoloInvito;
    private String chiaveCampionato;
    private String usernameUtenteCheHaInvitato;
    private Boolean invitoAccettato;

}
