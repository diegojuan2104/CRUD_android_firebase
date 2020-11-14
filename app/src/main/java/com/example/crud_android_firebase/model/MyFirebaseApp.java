package com.example.crud_android_firebase.model;

import com.google.firebase.database.FirebaseDatabase;

import java.security.cert.TrustAnchor;

public class MyFirebaseApp extends  android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
