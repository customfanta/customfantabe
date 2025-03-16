package it.customfanta.be.repository;

import it.customfanta.be.model.Campionato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampionatiRepository extends JpaRepository<Campionato, String> {

}
