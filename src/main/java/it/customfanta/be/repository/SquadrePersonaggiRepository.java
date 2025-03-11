package it.customfanta.be.repository;

import it.customfanta.be.model.SquadraPersonaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SquadrePersonaggiRepository extends JpaRepository<SquadraPersonaggio, String> {

    Optional<List<SquadraPersonaggio>> findByNomeSquadra(String nomeSquadra);

}
