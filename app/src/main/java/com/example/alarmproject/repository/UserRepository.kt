package com.example.alarmproject.repository

import com.example.alarmproject.model.firebase.BaseFirebaseModel
import com.example.alarmproject.model.user.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val db: FirebaseFirestore) {

    fun setUser(user: User): BaseFirebaseModel {
        var response = BaseFirebaseModel()
        val collection = db.collection("User")
        collection.document().set(user).addOnSuccessListener {
                response.message = "User Data Write Success"
                response.isSuccess = true
            }.addOnFailureListener { e -> response.exception = e }
        return response
    }

    @ExperimentalCoroutinesApi
    fun isExistsUser(uid : String) = callbackFlow {
        val collection = db.collection("User").whereEqualTo("uid", uid)
        collection.get().addOnSuccessListener {
            this@callbackFlow.trySendBlocking(true)
        }
        awaitClose { collection }
    }
}