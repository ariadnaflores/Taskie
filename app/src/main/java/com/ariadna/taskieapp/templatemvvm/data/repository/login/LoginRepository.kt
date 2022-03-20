package com.ariadna.taskieapp.templatemvvm.data.repository.login

import androidx.appcompat.app.AppCompatActivity
import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager

class LoginRepository(
    private val firebaseAuthManager: FirebaseAuthManager,
    private val prefsUser: PrefsUser
) {

    private fun saveUserEmail(email:String) {
        prefsUser.saveEmail(email)
    }

    private fun saveIsUserLoggedIn(user: Boolean) {
        prefsUser.saveIsUserLoggedIn(user)
    }

    private fun saveDataInPreferences(email: String, isUserLoggedIn: Boolean) {
        saveUserEmail(email = email)
        saveIsUserLoggedIn(user = isUserLoggedIn)
    }

    fun signIn(
        userEmail: String,
        userPassword: String,
        activity: AppCompatActivity,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        if (!prefsUser.getIsUserLoggedIn()) {
            firebaseAuthManager.signIn(userEmail, userPassword, activity) {
                saveDataInPreferences(email = userEmail, isUserLoggedIn = true)
                onSuccess.invoke()
            }
        } else {
            onFailed.invoke()
        }
    }
}