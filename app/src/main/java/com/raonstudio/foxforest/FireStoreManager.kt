package com.raonstudio.foxforest

import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object FireStoreManager {
    lateinit var db: FirebaseFirestore

    fun init() {
        db = FirebaseFirestore.getInstance()
    }

    fun createNewRoom(roomName: String, onSuccess: (gameRoom: GameRoom) -> Unit) {
        val newRoom = GameRoom(roomName)
        db.collection("rooms").document(newRoom.roomId).set(newRoom)
            .addOnSuccessListener {
                onSuccess.invoke(newRoom)
            }.addOnFailureListener {
                it.printStackTrace()
            }.addOnCanceledListener {
                Log.e("canceled", "fireasdasd")
            }
    }

    fun getRoomList(onSuccess: (gameRoom: List<GameRoom?>) -> Unit) {
        db.collection("rooms").whereEqualTo("state", GameState.WAITING.name)
            .addSnapshotListener(EventListener<QuerySnapshot> { snapShot, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@EventListener
                }
                snapShot?.documents?.map {
                    Log.d("roomlist", it.data.toString())
                    it.toObject(GameRoom::class.java)
                }?.let { onSuccess.invoke(it) }
            })
    }

    private fun onNetworkError() {
        db.app.applicationContext.run {
            networkErrorToast()
        }
    }
}