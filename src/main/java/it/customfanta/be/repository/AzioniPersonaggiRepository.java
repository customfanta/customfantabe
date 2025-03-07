package it.customfanta.be.repository;

import it.customfanta.be.model.AzionePersonaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AzioniPersonaggiRepository extends JpaRepository<AzionePersonaggio, Integer> {

    Optional<List<AzionePersonaggio>> findByNominativoPersonaggio(String nominativoPersonaggio);
}
