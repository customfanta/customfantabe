package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.InvitoCampionato;
import it.customfanta.be.model.annotations.FirebaseTableName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FirebaseTableName("InvitiCampionati")
public class InvitiCampionatiService extends BaseService {

    public InvitiCampionatiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public void save(InvitoCampionato invitoCampionato) {
        getCollection().document(invitoCampionato.getChiave()).set(invitoCampionato);
    }

    public InvitoCampionato findById(String chiave) {
        try {
            return getCollection().whereEqualTo("chiave", chiave).get().get().getDocuments().get(0).toObject(InvitoCampionato.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<InvitoCampionato> findByUsernameUtenteInvitato(String usernameUtenteInvitato) {
        List<InvitoCampionato> invitiCampionato = new ArrayList<>();
        try {
            getCollection().whereEqualTo("usernameUtenteInvitato", usernameUtenteInvitato).get().get().getDocuments().forEach(d -> invitiCampionato.add(d.toObject(InvitoCampionato.class)));
        } catch (Exception e) {
        }
        return invitiCampionato;
    }

    public List<InvitoCampionato> findByUsernameUtenteCheHaInvitato(String usernameUtenteCheHaInvitato) {
        List<InvitoCampionato> invitiCampionato = new ArrayList<>();
        try {
            getCollection().whereEqualTo("usernameUtenteCheHaInvitato", usernameUtenteCheHaInvitato).get().get().getDocuments().forEach(d -> invitiCampionato.add(d.toObject(InvitoCampionato.class)));
        } catch (Exception e) {
        }
        return invitiCampionato;
    }

    public List<InvitoCampionato> findByChiaveCampionato(String chiaveCampionato) {
        List<InvitoCampionato> invitiCampionato = new ArrayList<>();
        try {
            getCollection().whereEqualTo("chiaveCampionato", chiaveCampionato).get().get().getDocuments().forEach(d -> invitiCampionato.add(d.toObject(InvitoCampionato.class)));
        } catch (Exception e) {
        }
        return invitiCampionato;
    }

    public void deleteById(String chiave) {
        try {
            getCollection().document(chiave).delete().get();
        } catch (Exception ignored) {
        }
    }

}
