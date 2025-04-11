package it.customfanta.be.service;


import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.UtenteCampionato;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtentiCampionatiService extends BaseService {

    public UtentiCampionatiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public UtenteCampionato findByChiave(String chiave) {
        try {
            return getCollection().whereEqualTo("chiave", chiave).get().get().getDocuments().get(0).toObject(UtenteCampionato.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<UtenteCampionato> findByUsernameUtente(String usernameUtente) {
        List<UtenteCampionato> utentiCampionati = new ArrayList<>();
        try {
            getCollection().whereEqualTo("usernameUtente", usernameUtente).get().get().getDocuments().forEach(d -> utentiCampionati.add(d.toObject(UtenteCampionato.class)));
        } catch (Exception e) {
        }
        return utentiCampionati;
    }

    public List<UtenteCampionato> findByChiaveCampionato(String chiaveCampionato) {
        List<UtenteCampionato> utentiCampionati = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().forEach(d -> utentiCampionati.add(d.toObject(UtenteCampionato.class)));
        } catch (Exception e) {
        }
        return utentiCampionati;
    }

    public void save(UtenteCampionato utenteCampionato) {
        getCollection().document(utenteCampionato.getChiave()).set(utenteCampionato);
    }

    public void deleteByChiave(String chiave) {
        try {
            getCollection().document(chiave).delete().get();
        } catch (Exception ignored) {
        }
    }

}
