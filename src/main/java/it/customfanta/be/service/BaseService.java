package it.customfanta.be.service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import it.customfanta.be.model.annotations.FirebaseTableName;

public abstract class BaseService {

    protected final Firestore firestore;
    protected final String firebaseTableName;

    public BaseService(FirebaseApp firebaseApp) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
        FirebaseTableName firebaseTableNameAnnotation = this.getClass().getAnnotation(FirebaseTableName.class);
        firebaseTableName = firebaseTableNameAnnotation != null ? firebaseTableNameAnnotation.value() : this.getClass().getSimpleName();
    }

    protected CollectionReference getCollection() {
        return firestore.collection(firebaseTableName);
    }

}
