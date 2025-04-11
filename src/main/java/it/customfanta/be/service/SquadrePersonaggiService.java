package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.SquadraPersonaggio;
import it.customfanta.be.model.annotations.FirebaseTableName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FirebaseTableName("SquadrePersonaggi")
public class SquadrePersonaggiService extends BaseService {

    public SquadrePersonaggiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void saveSquadraPersonaggio(SquadraPersonaggio squadraPersonaggio) {
        getCollection().document(squadraPersonaggio.getChiave()).set(squadraPersonaggio);
    }

    public List<SquadraPersonaggio> readByChiaveSquadra(String chiaveSquadra) {
        List<SquadraPersonaggio> squadrePersonaggi = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveSquadra", chiaveSquadra).get().get().getDocuments().forEach(d -> squadrePersonaggi.add(d.toObject(SquadraPersonaggio.class)));
        } catch (Exception e) {
        }
        return squadrePersonaggi;
    }

    public void deleteSquadraPersonaggioById(String chiave) {
        try {
            getCollection().document(chiave).delete().get();
        } catch (Exception ignored) {
        }
    }

}
