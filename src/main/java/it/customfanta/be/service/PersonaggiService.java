package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Personaggio;
import it.customfanta.be.repository.PersonaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaggiService extends BaseService {

    @Autowired
    private PersonaggiRepository personaggiRepository;

    public PersonaggiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public Personaggio savePersonaggio(Personaggio personaggio) {
        return personaggiRepository.save(personaggio);
    }

    public List<Personaggio> readPersonaggi(String chiaveCampionato) {
        return personaggiRepository.findByChiaveCampionato(chiaveCampionato);
    }

}
