package it.customfanta.be.service;

import it.customfanta.be.model.Campionato;
import it.customfanta.be.repository.CampionatiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampionatiService extends BaseService {

    @Autowired
    private CampionatiRepository campionatiRepository;

    public Campionato findByChiave(String chiave) {
        return campionatiRepository.findById(chiave).orElse(null);
    }

    public void save(Campionato campionato) {
        campionatiRepository.save(campionato);
    }

    public void deleteByChiave(String chiave) {
        campionatiRepository.deleteById(chiave);
    }

    @Transactional
    public void dropTable() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS campionati")
                .executeUpdate();
    }

}
