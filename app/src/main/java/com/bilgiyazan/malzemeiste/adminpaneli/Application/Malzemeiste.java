package com.bilgiyazan.malzemeiste.adminpaneli.Application;

import com.google.firebase.database.FirebaseDatabase;

import android.app.Application;

/**
 * Created by Toshiba on 28/12/2016.
 */

public class Malzemeiste extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }
}
