package it.customfanta.be.repository;

import it.customfanta.be.model.UtenteCampionato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtentiCampionatiRepository extends JpaRepository<UtenteCampionato, String> {

    List<UtenteCampionato> findByUsernameUtente(String usernameUtente);
    List<UtenteCampionato> findByChiaveCampionato(String chiaveCampionato);

}
