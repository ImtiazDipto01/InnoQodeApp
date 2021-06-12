package com.imtiaz.innoqodetest.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.imtiaz.innoqodetest.data.model.AuthResponse
import com.imtiaz.taskmanager.utils.Resource

class AuthRepository(val auth: FirebaseAuth) {

    private val TAG = "AuthRepository"

    fun processLogin(
        livedata: MutableLiveData<Resource<AuthResponse?>?>,
        email: String,
        password: String
    ) {
        livedata.postValue(Resource.loading(null))

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    livedata.postValue(Resource.success(AuthResponse(user)))
                    Log.e(TAG, "signInWithEmail:success")
                } else {
                    livedata.postValue(Resource.error(AuthResponse(exception = task.exception?.message)))
                    Log.e(TAG, "New Exception: ${task.exception?.message}")
                }
            }
            .addOnFailureListener {
                livedata.postValue(Resource.error(AuthResponse(exception = it.message)))
                Log.e(TAG, "New Exception: ${it.message}")
            }
    }

    fun processSignUp(
        livedata: MutableLiveData<Resource<AuthResponse?>?>,
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        livedata.postValue(Resource.loading(null))

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e(TAG, "signUpWithFirebase: Yes")
                    createUserAtFireStore(email, firstName, lastName, it.result?.user, livedata)
                } else {
                    livedata.postValue(Resource.error(AuthResponse(exception = it.exception?.message)))
                    Log.e(TAG, "signUpWithFirebase: ${it.exception}")
                }
            }
            .addOnFailureListener {
                livedata.postValue(Resource.error(AuthResponse(exception = it.message)))
                Log.e(TAG, "signUpWithFirebase: ${it.message}")
            }
    }

    private fun createUserAtFireStore(
        email: String,
        firstName: String,
        lastName: String,
        user: FirebaseUser?,
        livedata: MutableLiveData<Resource<AuthResponse?>?>
    ) {

        val db = Firebase.firestore

        // Create a new user with a first and last name
        val newUser = hashMapOf(
            "first_name" to firstName,
            "last_name" to lastName,
            "email" to email,
            "profile_image" to "",
            "uid" to user?.uid
        )

        db.collection("users")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.e(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                livedata.postValue(Resource.success(AuthResponse(user)))
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
                livedata.postValue(Resource.error(AuthResponse(exception = e.message)))
            }


    }
}