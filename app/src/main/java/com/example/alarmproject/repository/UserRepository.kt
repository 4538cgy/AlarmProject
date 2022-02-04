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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserRepository @Inject constructor(private val db: FirebaseFirestore, private val storage: FirebaseStorage) {

    suspend fun setUser(user : User) = suspendCancellableCoroutine<BaseFirebaseModel> { continaution ->
        var response = BaseFirebaseModel()
        val collection = db.collection("User")
        collection.document(user.uid.toString()).set(user).addOnSuccessListener {
            response.message = "User Data Write Success"
            response.isSuccess = true
            continaution.resume(response)
        }.addOnFailureListener { e ->
            continaution.resumeWithException(e)
        }
    }

    suspend fun isExistsUser(uid : String) = suspendCancellableCoroutine<Boolean> { c ->
        val collection = db.collection("User").whereEqualTo("uid", uid)
        collection.get().addOnSuccessListener {
            if (!it.isEmpty)
            c.resume(true)
        }.addOnFailureListener {
            c.resumeWithException(it)
        }
    }

    suspend fun uploadProfileImage(uid: String, imageUri: Uri) = suspendCancellableCoroutine<BaseFirebaseModel> { c ->
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
                c.resume(responseModel)
            }
        }.addOnFailureListener {
            c.resumeWithException(it)
        }
    }

    suspend fun getMyProfile(uid: String) = suspendCancellableCoroutine<User> { c ->
        db.collection("User").document(uid).get().addOnCompleteListener {
            it?.let{
                if (it.isSuccessful){
                    val data = it.result.toObject(User::class.java)
                    c.resume(data?:return@addOnCompleteListener)
                }
            }
        }.addOnFailureListener {
            c.resumeWithException(it)
        }
    }
}