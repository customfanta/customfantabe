package it.customfanta.be.service;

import it.customfanta.be.model.Azione;
import it.customfanta.be.repository.AzioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AzioniService {

    @Autowired
    private AzioniRepository azioniRepository;

    public Azione saveAzione(Azione azione) {
        return azioniRepository.save(azione);
    }

    public Azione readByName(String nomeAzione) {
        return azioniRepository.findById(nomeAzione).orElse(null);
    }

    public List<Azione> realAll() {
        return azioniRepository.findAll();
    }

}
