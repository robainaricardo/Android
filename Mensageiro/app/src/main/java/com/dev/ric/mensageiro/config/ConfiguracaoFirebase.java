package com.dev.ric.mensageiro.config;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference firebaseReference;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFirebaseDB(){
        if (firebaseReference == null){
            firebaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseReference;
    }

    /**
     * Metodo que retorna a referencia da autenticação do firebase
     * @return autenticação do firebase.
     */
    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            firebaseAuth = firebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

}
