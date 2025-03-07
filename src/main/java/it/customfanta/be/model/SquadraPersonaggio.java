package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "squadre_personaggi")
@Data
public class SquadraPersonaggio {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String nomeSquadra;
    private String nominativoPersonaggio;

}
