package com.raonstudio.foxforest

import android.app.Application

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseAuthManager.init()
        FireStoreRepository.init()
        SharedPreferenceManager.init(this)
    }
}