package com.ariadna.taskieapp.templatemvvm.data.remote

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FirebaseAuthManager {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signIn(
        userEmail: String,
        userPassword: String,
        activity: AppCompatActivity,
        onSuccess: () -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.e("Task message", "Successful.......")
                    onSuccess.invoke()
                }
            }.addOnFailureListener {
                Log.e("Task message error", "${it.localizedMessage}")
            }
    }

    fun createUser(
        userEmail: String,
        userPassword: String,
        activity: AppCompatActivity,
        onSuccess: () -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.e("Task message", "Successful user creation.....")
                    sendEmailVerification()
                    onSuccess.invoke()
                } else {
                    Log.e("Task message", "Unsuccessful user.....", task.exception)
                }
            }
    }

    private fun sendEmailVerification() {
        val user = Firebase.auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("Task message", "Email sent.")
                }
            }
    }

    fun logOut() {
        Firebase.auth.signOut()
    }
}