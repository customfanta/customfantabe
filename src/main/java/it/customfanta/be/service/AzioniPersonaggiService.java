package it.customfanta.be.service;

import it.customfanta.be.model.AzionePersonaggio;
import it.customfanta.be.repository.AzioniPersonaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AzioniPersonaggiService {

    @Autowired
    private AzioniPersonaggiRepository azioniPersonaggiRepository;

    public AzionePersonaggio saveAzionePersonaggio(AzionePersonaggio azionePersonaggio) {
        return azioniPersonaggiRepository.save(azionePersonaggio);
    }

    public List<AzionePersonaggio> readByNomePersonaggio(String nomePersonaggio) {
        return azioniPersonaggiRepository.findByNominativoPersonaggio(nomePersonaggio).orElse(new ArrayList<>());
    }


}
