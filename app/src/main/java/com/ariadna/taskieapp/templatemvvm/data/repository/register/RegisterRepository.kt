package com.ariadna.taskieapp.templatemvvm.data.repository.register

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterRepository (
    private val firebaseAuthManager: FirebaseAuthManager,
    private val prefsUser: PrefsUser
    ){

    fun createUser(
        userEmail: String,
        userPassword: String,
        activity: AppCompatActivity,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        if (!prefsUser.getIsUserLoggedIn()) {
            firebaseAuthManager.createUser(userEmail, userPassword, activity){
                sendEmailVerification()
                onSuccess.invoke()
            }
        } else {
            onFailed.invoke()
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
}