package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Squadra;
import it.customfanta.be.repository.SquadreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SquadreService extends BaseService {

    @Autowired
    private SquadreRepository squadreRepository;

    public SquadreService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public Squadra saveSquadra(Squadra squadra) {
        return squadreRepository.save(squadra);
    }

    public Squadra readSquadraByUtente(String usernameUtente, String chiaveCampionato) {
        return squadreRepository.findByUsernameUtenteAndChiaveCampionato(usernameUtente, chiaveCampionato).orElse(null);
    }

    public void deleteSquadraById(String chiave) {
        squadreRepository.deleteById(chiave);
    }

}
