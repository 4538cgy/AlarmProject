package com.example.alarmproject.repository

import android.net.Uri
import com.example.alarmproject.model.firebase.BaseFirebaseModel
import com.example.alarmproject.model.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val db: FirebaseFirestore, private val storage: FirebaseStorage) {

    fun setUser(user: User): BaseFirebaseModel {
        var response = BaseFirebaseModel()
        val collection = db.collection("User")
        collection.document(user.uid.toString()).set(user).addOnSuccessListener {
            response.message = "User Data Write Success"
            response.isSuccess = true
        }.addOnFailureListener { e -> response.exception = e }
        return response
    }

    @ExperimentalCoroutinesApi
    fun isExistsUser(uid: String) = callbackFlow {
        val collection = db.collection("User").whereEqualTo("uid", uid)
        collection.get().addOnSuccessListener {
            this@callbackFlow.trySendBlocking(true)
        }
        awaitClose { collection }
    }

    @ExperimentalCoroutinesApi
    fun uploadProfileImage(uid: String, imageUri: Uri) = callbackFlow {
        val eventListener = storage.reference.child("UserProfileImages").child(uid)
        val responseModel = BaseFirebaseModel()
        eventListener.putFile(imageUri).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            eventListener.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                responseModel.message = "Profile Image Upload Success"
                responseModel.isSuccess = true
                responseModel.storageModel = BaseFirebaseModel.StorageModel(task.result.toString())
                this@callbackFlow.trySendBlocking(responseModel)
            }
        }

        awaitClose { eventListener }
    }

    @ExperimentalCoroutinesApi
    fun getMyProfile(uid: String) = callbackFlow {
        val eventListener = db.collection("User").document(uid).get().addOnCompleteListener {
            it?.let{
                if (it.isSuccessful){
                    val data = it.result.toObject(User::class.java)
                    this@callbackFlow.trySendBlocking(data)
                }
            }
        }
        awaitClose { eventListener }
    }
}