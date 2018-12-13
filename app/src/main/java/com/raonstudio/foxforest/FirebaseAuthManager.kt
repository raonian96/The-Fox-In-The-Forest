package com.raonstudio.foxforest

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

object FirebaseAuthManager {
    private const val AUTH_PASSWORD = "TheFoxInTheForest"
    private val email: String
        get() = "${SharedPreferenceManager.userId.replace("-", "")}@raonstudio.com"

    lateinit var auth: FirebaseAuth

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun init() {
        auth = FirebaseAuth.getInstance()
    }

    private fun signUp(onSuccess: (user: FirebaseUser) -> Unit) {
        auth.createUserWithEmailAndPassword(email, AUTH_PASSWORD)
                .addOnSuccessListener {
                    onSuccess.invoke(it.user)
                }.addOnFailureListener {
                    it.printStackTrace()
                }
    }

    fun signIn(onSuccess: (user: FirebaseUser) -> Unit, onNetworkError: () -> Unit) {
        Log.d("uuid", SharedPreferenceManager.userId)
        auth.signInWithEmailAndPassword(email, AUTH_PASSWORD)
                .addOnSuccessListener {
                    onSuccess.invoke(it.user)
                }.addOnFailureListener {
                    when (it) {
                        is FirebaseNetworkException -> {
                            onNetworkError.invoke()
                        }
                        is FirebaseAuthInvalidUserException -> {
                            signUp(onSuccess)
                        }
                        else -> {
                            it.printStackTrace()
                        }
                    }
                }
    }
}