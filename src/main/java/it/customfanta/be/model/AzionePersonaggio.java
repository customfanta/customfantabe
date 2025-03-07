package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "azioni_personaggi")
@Data
public class AzionePersonaggio {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String azione;
    private String nominativoPersonaggio;
    private String dataEsecuzione;

}
