package it.customfanta.be.service;

import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import it.customfanta.be.model.Utente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private final Firestore firestore;

    public FirestoreService(FirebaseApp firebaseApp) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
    }

    public void aggiungiUtente(Utente utente) {
        utente.setUsername_lower(utente.getUsername().toLowerCase());
        utente.setNome_lower(utente.getNome().toLowerCase());
        utente.setMail_lower(utente.getMail().toLowerCase());
        firestore.collection("utenti").document(utente.getUsername()).set(utente);
    }

    public List<Utente> recuperaUtenteByUsernameOrMail(Utente utente) throws ExecutionException, InterruptedException {
        List<Utente> utenti = new ArrayList<>();
        firestore.collection("utenti").where(Filter.or(Filter.equalTo("username_lower", utente.getUsername().toLowerCase()), Filter.equalTo("mail_lower", utente.getMail().toLowerCase()))).get().get().forEach(d -> utenti.add(d.toObject(Utente.class)));
        return utenti;
    }

    public Utente recuperaUtente(String username) throws ExecutionException, InterruptedException {
        return firestore.collection("utenti").whereEqualTo("username_lower", username.toLowerCase()).get().get().getDocuments().get(0).toObject(Utente.class);
    }

    public Utente recuperaUtenteByUuidMailCertificazione(String uuidMailCertificazione) throws ExecutionException, InterruptedException {
        return firestore.collection("utenti").whereEqualTo("uuidMailCertificazione", uuidMailCertificazione).get().get().getDocuments().get(0).toObject(Utente.class);
    }

    public List<Utente> recuperaUtentiByUsernameContainingIgnoreCaseOrNomeContainingIgnoreCaseOrMailContainingIgnoreCase(String username, String nome, String mail) throws ExecutionException, InterruptedException {
        List<Utente> utenti = new ArrayList<>();
        firestore.collection("utenti").where(
                Filter.or(Filter.equalTo("username_lower", username.toLowerCase()), Filter.equalTo("nome_lower", nome.toLowerCase()), Filter.equalTo("mail_lower", mail.toLowerCase()))
        ).limit(5).get().get().forEach(d -> utenti.add(d.toObject(Utente.class)));
        return utenti;
    }

}
