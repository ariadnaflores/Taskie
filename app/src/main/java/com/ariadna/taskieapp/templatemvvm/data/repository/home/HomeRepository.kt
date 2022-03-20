package com.ariadna.taskieapp.templatemvvm.data.repository.home

import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeRepository(
    private val firebaseAuthManager: FirebaseAuthManager
) {
    fun logOut() {
        firebaseAuthManager.logOut()
    }

}