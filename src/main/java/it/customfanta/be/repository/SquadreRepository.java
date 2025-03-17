package it.customfanta.be.repository;

import it.customfanta.be.model.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SquadreRepository extends JpaRepository<Squadra, String> {

    Optional<Squadra> findByUsernameUtenteAndChiaveCampionato(String usernameUtente, String chiaveCampionato);

}
