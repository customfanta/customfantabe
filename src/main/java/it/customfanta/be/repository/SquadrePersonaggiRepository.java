package it.customfanta.be.repository;

import it.customfanta.be.model.SquadraPersonaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SquadrePersonaggiRepository extends JpaRepository<SquadraPersonaggio, Integer> {

}
