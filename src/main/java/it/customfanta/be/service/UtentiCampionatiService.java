package it.customfanta.be.service;


import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.UtenteCampionato;
import it.customfanta.be.repository.UtentiCampionatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtentiCampionatiService extends BaseService {

    @Autowired
    private UtentiCampionatiRepository utentiCampionatiRepository;

    public UtentiCampionatiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public UtenteCampionato findByChiave(String chiave) {
        return utentiCampionatiRepository.findById(chiave).orElse(null);
    }

    public List<UtenteCampionato> findByUsernameUtente(String usernameUtente) {
        return utentiCampionatiRepository.findByUsernameUtente(usernameUtente);
    }

    public List<UtenteCampionato> findByChiaveCampionato(String chiaveCampionato) {
        return utentiCampionatiRepository.findByChiaveCampionato(chiaveCampionato);
    }

    public void save(UtenteCampionato utenteCampionato) {
        utentiCampionatiRepository.save(utenteCampionato);
    }

    public void deleteByChiave(String chiave) {
        utentiCampionatiRepository.deleteById(chiave);
    }

}
