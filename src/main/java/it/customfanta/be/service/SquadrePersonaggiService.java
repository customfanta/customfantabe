package it.customfanta.be.service;

import it.customfanta.be.model.SquadraPersonaggio;
import it.customfanta.be.repository.SquadrePersonaggiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SquadrePersonaggiService extends BaseService {

    @Autowired
    private SquadrePersonaggiRepository squadrePersonaggiRepository;

    public SquadraPersonaggio saveSquadraPersonaggio(SquadraPersonaggio squadraPersonaggio) {
        return squadrePersonaggiRepository.save(squadraPersonaggio);
    }

    public List<SquadraPersonaggio> readByNomeSquadra(String nomeSquadra) {
        return squadrePersonaggiRepository.findByNomeSquadra(nomeSquadra).orElse(new ArrayList<>());
    }

    public void deleteSquadraPersonaggioById(String chiave) {
        squadrePersonaggiRepository.deleteById(chiave);
    }

    @Transactional
    public void dropSquadrePersonaggi() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS squadre_personaggi")
                .executeUpdate();
    }

}
