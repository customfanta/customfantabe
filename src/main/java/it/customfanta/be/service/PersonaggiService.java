package it.customfanta.be.service;

import it.customfanta.be.model.Personaggio;
import it.customfanta.be.repository.PersonaggiRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaggiService extends BaseService {

    @Autowired
    private PersonaggiRepository personaggiRepository;

    public Personaggio savePersonaggio(Personaggio personaggio) {
        return personaggiRepository.save(personaggio);
    }

    public List<Personaggio> readPersonaggi(String chiaveCampionato) {
        return personaggiRepository.findByChiaveCampionato(chiaveCampionato);
    }

    @Transactional
    public void dropPersonaggi() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS personaggi")
                .executeUpdate();
    }

}
