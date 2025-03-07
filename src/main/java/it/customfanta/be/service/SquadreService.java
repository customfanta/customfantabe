package it.customfanta.be.service;

import it.customfanta.be.model.Squadra;
import it.customfanta.be.repository.SquadreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SquadreService {

    @Autowired
    private SquadreRepository squadreRepository;

    public Squadra saveSquadra(Squadra squadra) {
        return squadreRepository.save(squadra);
    }

}
