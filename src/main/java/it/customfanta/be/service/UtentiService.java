package it.customfanta.be.service;

import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import it.customfanta.be.model.Utente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class UtentiService extends BaseService {

    private final Firestore firestore;

    public UtentiService(FirebaseApp firebaseApp) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
    }

    public void aggiungiUtente(Utente utente) {
        utente.setUsername_lower(utente.getUsername().toLowerCase());
        utente.setNome_lower(utente.getNome().toLowerCase());
        utente.setMail_lower(utente.getMail().toLowerCase());
        firestore.collection("utenti").document(utente.getUsername()).set(utente);
    }

    public Utente recuperaUtenteByUsernameOrMail(Utente utente) {
        try {
            return firestore.collection("utenti").where(Filter.or(Filter.equalTo("username_lower", utente.getUsername().toLowerCase()), Filter.equalTo("mail_lower", utente.getMail().toLowerCase()))).get().get().getDocuments().get(0).toObject(Utente.class);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public Utente recuperaUtente(String username) {
        try {
            return firestore.collection("utenti").whereEqualTo("username_lower", username.toLowerCase()).get().get().getDocuments().get(0).toObject(Utente.class);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public Utente recuperaUtenteByUuidMailCertificazione(String uuidMailCertificazione) {
        try {
            return firestore.collection("utenti").whereEqualTo("uuidMailCertificazione", uuidMailCertificazione).get().get().getDocuments().get(0).toObject(Utente.class);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<Utente> recuperaUtentiByUsernameContainingIgnoreCaseOrNomeContainingIgnoreCaseOrMailContainingIgnoreCase(String username, String nome, String mail, String usernameUtenteLoggato, Set<String> usernameUtentiInCampionato) {
        List<Utente> utenti = new ArrayList<>();

        String usernameLower = username.toLowerCase();
        String nomeLower = nome.toLowerCase();
        String mailLower = mail.toLowerCase();
        String usernameUtenteLoggatoLower = usernameUtenteLoggato.toLowerCase();
        Set<String> usernameUtentiInCampionatoLower = usernameUtentiInCampionato.stream().map(String::toLowerCase).collect(Collectors.toSet());

        int i = 0;
        try {
            for(QueryDocumentSnapshot doc : firestore.collection("utenti").get().get().getDocuments()) {
                Utente utente = doc.toObject(Utente.class);
                if ((utente.getUsername_lower().contains(usernameLower) || utente.getNome_lower().contains(nomeLower) || utente.getMail_lower().contains(mailLower)) && !utente.getUsername_lower().equals(usernameUtenteLoggatoLower) && !usernameUtentiInCampionatoLower.contains(utente.getUsername_lower())) {
                    Utente addingUser = new Utente();
                    addingUser.setUsername(utente.getUsername());
                    utenti.add(addingUser);
                    i++;
                }
                if(i >= 5) {
                    break;
                }
            }
        } catch (InterruptedException | ExecutionException ignored) {
        }

        return utenti;
    }

}
