package com.raonstudio.foxforest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FirebaseAuthManager {
    private const val AUTH_PASSWORD = "TheFoxInTheForest"
    lateinit var auth: FirebaseAuth

    var currentUser: FirebaseUser?
        get() = auth.currentUser
        private set(value) {}

    fun init() {
        auth = FirebaseAuth.getInstance()
    }

    private fun signUp(onSuccess: (user: FirebaseUser) -> Unit) {
        auth.createUserWithEmailAndPassword("${SharedPreferenceManager.userId.replace("-", "")}@raonstudio.com", AUTH_PASSWORD)
            .addOnSuccessListener {
                onSuccess.invoke(it.user)
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun signIn(onSuccess: (user: FirebaseUser) -> Unit) {
        Log.d("uuid", SharedPreferenceManager.userId)
        auth.signInWithEmailAndPassword("${SharedPreferenceManager.userId.replace("-", "")}@raonstudio.com", AUTH_PASSWORD)
            .addOnSuccessListener {
                onSuccess.invoke(it.user)
            }.addOnFailureListener {
                it.printStackTrace()
                signUp(onSuccess)
            }
    }
}