package com.ylt.medic

import android.app.Application
import timber.log.Timber

class MedicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //Stetho.initializeWithDefaults(this);
    }
}