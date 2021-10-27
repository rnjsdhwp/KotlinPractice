package com.example.sensordata2

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Realm.setDefaultConfiguration(getRealmConfig())
        Realm.init(this)
        //Realm.init(applicationContext);
        var config = RealmConfiguration.Builder().name("Sensor1.realm").schemaVersion(0).build()
        Realm.setDefaultConfiguration(config)

    }
//    private fun getRealmConfig(): RealmConfiguration? {
//        return RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
//    }
}