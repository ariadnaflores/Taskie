package com.ariadna.taskieapp.templatemvvm.data.repository.home

import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager

class HomeRepository(
    private val firebaseAuthManager: FirebaseAuthManager,
    private val prefsUser: PrefsUser
) {
    fun logOut() {
        firebaseAuthManager.logOut()
        prefsUser.wipe()
    }

}