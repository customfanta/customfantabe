package it.customfanta.be.repository;

import it.customfanta.be.controller.UsernameUser;
import it.customfanta.be.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, String> {

    Optional<Utente> findByUsernameOrMail(String username, String mail);
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByUuidMailCertificazione(String uuidMailCertificazione);

    List<UsernameUser> findByUsernameContainingIgnoreCaseOrNomeContainingIgnoreCaseOrMailContainingIgnoreCase(String username, String nome, String mail);
}
