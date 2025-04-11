package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.AzionePersonaggio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AzioniPersonaggiService extends BaseService {

    public AzioniPersonaggiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void saveAzionePersonaggio(AzionePersonaggio azionePersonaggio) {
        getCollection().document(azionePersonaggio.getChiaveAzione()).set(azionePersonaggio);
    }

    public List<AzionePersonaggio> readByChiavePersonaggio(String chiavePersonaggio) {
        List<AzionePersonaggio> azioniPersonaggi = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiavePersonaggio", chiavePersonaggio).get().get().getDocuments().forEach(d -> azioniPersonaggi.add(d.toObject(AzionePersonaggio.class)));
        } catch (Exception ignored) {
        }
        return azioniPersonaggi;
    }

}
