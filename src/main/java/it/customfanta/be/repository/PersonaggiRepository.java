package it.customfanta.be.repository;

import it.customfanta.be.model.Personaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonaggiRepository extends JpaRepository<Personaggio, String> {

    List<Personaggio> findByChiaveCampionato(String chiaveCampionato);
}
