package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Squadra;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SquadreService extends BaseService {

    public SquadreService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void saveSquadra(Squadra squadra) {
        getCollection().document(squadra.getChiave()).set(squadra);
    }

    public List<Squadra> findByChiaveCampionato(String chiaveCampionato) {
        List<Squadra> squadre = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().forEach(d -> squadre.add(d.toObject(Squadra.class)));
        } catch (Exception e) {
        }
        return squadre;
    }

    public Squadra readSquadraByUtente(String usernameUtente, String chiaveCampionato) {
        try {
            return getCollection().whereEqualTo("usernameUtente", usernameUtente).whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().get(0).toObject(Squadra.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteSquadraById(String chiave) {
        try {
            getCollection().document(chiave).delete().get();
        } catch (Exception ignored) {
        }
    }

}
