package it.customfanta.be.service;

import com.google.cloud.firestore.CollectionReference;
import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Azione;
import it.customfanta.be.model.annotations.FirebaseTableName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FirebaseTableName("Azioni")
public class AzioniService extends BaseService {

    public AzioniService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void saveAzione(Azione azione) {
        getCollection().document(azione.getChiave()).set(azione);
    }

    public void saveAllAzione(List<Azione> azioni) {
        CollectionReference collection = getCollection();
        for(Azione azione : azioni) {
            collection.document(azione.getChiave()).set(azione);
        }
    }

    public Azione readByChiave(String chiave) {
        try {
            return getCollection().whereEqualTo("chiave", chiave).get().get().getDocuments().get(0).toObject(Azione.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Azione> realAll(String chiaveCampionato) {
        List<Azione> azioni = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().forEach(d -> azioni.add(d.toObject(Azione.class)));
        } catch (Exception e) {
        }
        return azioni;
    }

}
