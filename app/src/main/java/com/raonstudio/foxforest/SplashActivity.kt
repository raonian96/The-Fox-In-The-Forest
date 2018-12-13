package com.raonstudio.foxforest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FirebaseAuthManager.signIn ({
            startActivity(MainActivity::class.java)
            finish()
        }, {
            toast(R.string.network_error_message)
        })
    }
}
