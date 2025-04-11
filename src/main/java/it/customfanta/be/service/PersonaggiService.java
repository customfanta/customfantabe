package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Personaggio;
import it.customfanta.be.model.annotations.FirebaseTableName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FirebaseTableName("Personaggi")
public class PersonaggiService extends BaseService {

    public PersonaggiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void savePersonaggio(Personaggio personaggio) {
        getCollection().document(personaggio.getChiave()).set(personaggio);
    }

    public Personaggio readByChiave(String chiave) {
        try {
            return getCollection().whereEqualTo("chiave", chiave).get().get().getDocuments().get(0).toObject(Personaggio.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Personaggio> readPersonaggi(String chiaveCampionato) {
        List<Personaggio> personaggi = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().forEach(d -> personaggi.add(d.toObject(Personaggio.class)));
        } catch (Exception e) {
        }
        return personaggi;
    }

}
