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
        onFailed: (messageError: String) -> Unit
    ) {
        if (!prefsUser.getIsUserLoggedIn()) {
            firebaseAuthManager.createUser(
                userEmail = userEmail,
                userPassword = userPassword,
                activity = activity,
                onSuccess = {
                    sendEmailVerification()
                    onSuccess.invoke()
                },
                onFailed =  {
                onFailed.invoke(it)
            })
        } else {
                onFailed.invoke("datos ya registrados")
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