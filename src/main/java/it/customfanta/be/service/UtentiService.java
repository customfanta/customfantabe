package it.customfanta.be.service;

import it.customfanta.be.model.Utente;
import it.customfanta.be.repository.UtentiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtentiService extends BaseService {

    @Autowired
    private UtentiRepository utentiRepository;

    public Utente saveUtente(Utente utente) {
        return utentiRepository.save(utente);
    }

    public Utente findUtente(Utente findUtenteRequest) {
        return utentiRepository.findByUsernameOrMail(findUtenteRequest.getUsername(), findUtenteRequest.getMail()).orElse(null);
    }

    public Utente findUtenteByUUIDMail(String uuidMailCertificazione) {
        return utentiRepository.findByUuidMailCertificazione(uuidMailCertificazione).orElse(null);
    }

    public List<Utente> findAll() {
        return utentiRepository.findAll();
    }

    public void deleteAll() {
        utentiRepository.deleteAll();
    }

    public Utente findById(String username) {
        return utentiRepository.findById(username).orElse(null);
    }

    public void deleteByID(String username) {
        utentiRepository.deleteById(username);
    }

    @Transactional
    public void dropTable() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS utenti")
                .executeUpdate();
    }

}
