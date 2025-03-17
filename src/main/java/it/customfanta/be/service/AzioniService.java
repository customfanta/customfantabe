package it.customfanta.be.service;

import it.customfanta.be.model.Azione;
import it.customfanta.be.repository.AzioniRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AzioniService extends BaseService {

    @Autowired
    private AzioniRepository azioniRepository;

    public Azione saveAzione(Azione azione) {
        return azioniRepository.save(azione);
    }

    public Azione readByChiave(String chiave) {
        return azioniRepository.findById(chiave).orElse(null);
    }

    public List<Azione> realAll(String chiaveCampionato) {
        return azioniRepository.findByChiaveCampionato(chiaveCampionato);
    }

    @Transactional
    public void dropAzioniService() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS azioni")
                .executeUpdate();
    }

}
