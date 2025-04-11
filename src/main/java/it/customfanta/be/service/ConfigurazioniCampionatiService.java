package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.ConfigurazioneCampionato;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigurazioniCampionatiService extends BaseService {

    public ConfigurazioniCampionatiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void save(ConfigurazioneCampionato configurazioneCampionato) {
        getCollection().document(configurazioneCampionato.getChiave()).set(configurazioneCampionato);
    }

    public List<ConfigurazioneCampionato> findByChiaveCampionato(String chiaveCampionato) {
        List<ConfigurazioneCampionato> configurazioniCampionato = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().forEach(d -> configurazioniCampionato.add(d.toObject(ConfigurazioneCampionato.class)));
        } catch (Exception e) {
        }
        return configurazioniCampionato;
    }
}
