package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "squadre_personaggi")
@Data
public class SquadraPersonaggio {

    @Id
    private String chiave;

    private String nomeUtente;
    private String nomeSquadra;
    private String nominativoPersonaggio;

}
