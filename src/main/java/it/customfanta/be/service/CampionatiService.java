package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Campionato;
import it.customfanta.be.repository.CampionatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampionatiService extends BaseService {

    @Autowired
    private CampionatiRepository campionatiRepository;

    public CampionatiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public Campionato findByChiave(String chiave) {
        return campionatiRepository.findById(chiave).orElse(null);
    }

    public void save(Campionato campionato) {
        campionatiRepository.save(campionato);
    }

    public void deleteByChiave(String chiave) {
        campionatiRepository.deleteById(chiave);
    }

}
