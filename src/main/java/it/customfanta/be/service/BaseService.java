package it.customfanta.be.service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

public abstract class BaseService {

    protected final Firestore firestore;

    public BaseService(FirebaseApp firebaseApp) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
    }

    protected CollectionReference getCollection() {
        return firestore.collection(this.getClass().getSimpleName());
    }

}
