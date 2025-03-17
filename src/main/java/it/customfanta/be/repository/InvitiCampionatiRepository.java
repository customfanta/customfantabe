package it.customfanta.be.repository;

import it.customfanta.be.model.InvitoCampionato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InvitiCampionatiRepository extends JpaRepository<InvitoCampionato, String> {

    List<InvitoCampionato> findByUsernameUtenteInvitato(String usernameUtenteInvitato);
    List<InvitoCampionato> findByUsernameUtenteCheHaInvitato(String usernameUtenteCheHaInvitato);
    List<InvitoCampionato> findByChiaveCampionato(String chiaveCampionato);

}
