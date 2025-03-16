package it.customfanta.be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "azioni_personaggi")
@Data
public class AzionePersonaggio {

    @Id
    private String chiave;

    private String chiaveAzione;
    private String chiavePersonaggio;
    private String dataEsecuzione;

}
