package it.customfanta.be.service;

import it.customfanta.be.model.AzionePersonaggio;
import it.customfanta.be.repository.AzioniPersonaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AzioniPersonaggiService {

    @Autowired
    private AzioniPersonaggiRepository azioniPersonaggiRepository;

    public AzionePersonaggio saveAzionePersonaggio(AzionePersonaggio azionePersonaggio) {
        return azioniPersonaggiRepository.save(azionePersonaggio);
    }

}
