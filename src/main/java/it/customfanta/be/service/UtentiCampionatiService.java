package it.customfanta.be.service;


import it.customfanta.be.model.Campionato;
import it.customfanta.be.model.UtenteCampionato;
import it.customfanta.be.repository.UtentiCampionatiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtentiCampionatiService extends BaseService {

    @Autowired
    private UtentiCampionatiRepository utentiCampionatiRepository;

    public List<UtenteCampionato> findByUsernameUtente(String usernameUtente) {
        return utentiCampionatiRepository.findByUsernameUtente(usernameUtente);
    }

    public List<UtenteCampionato> findByChiaveCampionato(String chiaveCampionato) {
        return utentiCampionatiRepository.findByChiaveCampionato(chiaveCampionato);
    }

    public void save(UtenteCampionato utenteCampionato) {
        utentiCampionatiRepository.save(utenteCampionato);
    }

    public void deleteByChiave(String chiave) {
        utentiCampionatiRepository.deleteById(chiave);
    }

    @Transactional
    public void dropTable() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS utenti_campionati")
                .executeUpdate();
    }

}
