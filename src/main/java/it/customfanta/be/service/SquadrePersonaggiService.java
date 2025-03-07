package it.customfanta.be.service;

import it.customfanta.be.model.SquadraPersonaggio;
import it.customfanta.be.repository.SquadrePersonaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SquadrePersonaggiService {

    @Autowired
    private SquadrePersonaggiRepository squadrePersonaggiRepository;

    public SquadraPersonaggio saveSquadraPersonaggio(SquadraPersonaggio squadraPersonaggio) {
        return squadrePersonaggiRepository.save(squadraPersonaggio);
    }

}
