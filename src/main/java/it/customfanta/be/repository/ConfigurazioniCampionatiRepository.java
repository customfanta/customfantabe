package it.customfanta.be.repository;

import it.customfanta.be.model.ConfigurazioneCampionato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConfigurazioniCampionatiRepository extends JpaRepository<ConfigurazioneCampionato, String> {

    List<ConfigurazioneCampionato> findByChiaveCampionato(String chiaveCampionato);
}
