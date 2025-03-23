package it.customfanta.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "configurazioni_campionati")
@Data
public class ConfigurazioneCampionato {

    @Id
    private String chiave;

    private String chiaveCampionato;
    private String chiaveConfigurazione;
    private String valoreConfigurazione;

}
