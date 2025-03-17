package it.customfanta.be.service;

import it.customfanta.be.model.Squadra;
import it.customfanta.be.repository.SquadreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SquadreService extends BaseService {

    @Autowired
    private SquadreRepository squadreRepository;

    public Squadra saveSquadra(Squadra squadra) {
        return squadreRepository.save(squadra);
    }

    public Squadra readSquadraByUtente(String usernameUtente, String chiaveCampionato) {
        return squadreRepository.findByUsernameUtenteAndChiaveCampionato(usernameUtente, chiaveCampionato).orElse(null);
    }

    public void deleteSquadraById(String chiave) {
        squadreRepository.deleteById(chiave);
    }

    @Transactional
    public void dropSquadre() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS squadre")
                .executeUpdate();
    }

}
