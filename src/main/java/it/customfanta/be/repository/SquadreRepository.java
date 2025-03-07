package it.customfanta.be.repository;

import it.customfanta.be.model.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SquadreRepository extends JpaRepository<Squadra, String> {

}
