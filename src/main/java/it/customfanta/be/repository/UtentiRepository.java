package it.customfanta.be.repository;

import it.customfanta.be.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, String> {

    Optional<Utente> findByUsernameOrMail(String username, String mail);

}
