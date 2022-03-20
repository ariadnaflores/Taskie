package com.ariadna.taskieapp.templatemvvm.data.repository

import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser

class TaskieRepository(
    private val prefsUser: PrefsUser
) {
    fun getUserEmail(): String {
        return prefsUser.getEmail()
    }
    fun saveUserEmail(email:String) {
        prefsUser.saveEmail(email)
    }
    fun getIsUserLoggedIn(): Boolean {
        return prefsUser.getIsUserLoggedIn()
    }
    fun saveIsUserLoggedIn(user:Boolean){
        prefsUser.saveIsUserLoggedIn(user)
    }
    fun wipe() {
        prefsUser.wipe()
    }
}