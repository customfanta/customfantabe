package it.customfanta.be.repository;

import it.customfanta.be.model.AzionePersonaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AzioniPersonaggiRepository extends JpaRepository<AzionePersonaggio, Integer> {

}
