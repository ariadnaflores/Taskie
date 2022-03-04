package com.ariadna.taskieapp.templatemvvm.data.local

import android.content.Context
import com.ariadna.taskieapp.templatemvvm.application.TaskieApplication

class PrefsUser {

    private val SHARED_NAME = "MyDtb"
    private val SHARED_USER_EMAIL = "userEmail"
    private val SHARED_USER_LOGGED_IN = "isUserLoggedIn"

    private val storage = TaskieApplication.getApplicationContext().getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    fun saveEmail(email:String){
        storage.edit().putString(SHARED_USER_EMAIL, email).apply()
    }

    fun saveIsUserLoggedIn(user:Boolean){
        storage.edit().putBoolean(SHARED_USER_LOGGED_IN,user).apply()
    }

    fun getEmail():String{
        return storage.getString(SHARED_USER_EMAIL, "")!!
    }

    fun getIsUserLoggedIn():Boolean{
        return storage.getBoolean(SHARED_USER_LOGGED_IN,false)
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}