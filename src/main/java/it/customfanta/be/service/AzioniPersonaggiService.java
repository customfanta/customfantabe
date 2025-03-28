package it.customfanta.be.service;

import it.customfanta.be.model.AzionePersonaggio;
import it.customfanta.be.repository.AzioniPersonaggiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AzioniPersonaggiService extends BaseService {

    @Autowired
    private AzioniPersonaggiRepository azioniPersonaggiRepository;

    public AzionePersonaggio saveAzionePersonaggio(AzionePersonaggio azionePersonaggio) {
        return azioniPersonaggiRepository.save(azionePersonaggio);
    }

    public List<AzionePersonaggio> readByChiavePersonaggio(String chiavePersonaggio) {
        return azioniPersonaggiRepository.findByChiavePersonaggio(chiavePersonaggio).orElse(new ArrayList<>());
    }

    @Transactional
    public void dropAzioniPersonaggi() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS azioni_personaggi")
                .executeUpdate();
    }

}
