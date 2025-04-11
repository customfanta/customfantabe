package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.Campionato;
import org.springframework.stereotype.Service;

@Service
public class CampionatiService extends BaseService {

    public CampionatiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public Campionato findByChiave(String chiave) {
        try {
            return getCollection().whereEqualTo("chiave", chiave).get().get().getDocuments().get(0).toObject(Campionato.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Campionato campionato) {
        getCollection().document(campionato.getChiave()).set(campionato);
    }

    public void deleteByChiave(String chiave) {
        try {
            getCollection().document(chiave).delete().get();
        } catch (Exception ignored) {
        }
    }

}
