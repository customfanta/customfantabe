package it.customfanta.be.repository;

import it.customfanta.be.model.Azione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AzioniRepository extends JpaRepository<Azione, String> {

    List<Azione> findByChiaveCampionato(String chiaveCampionato);
}
