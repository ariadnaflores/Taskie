package com.ariadna.taskieapp.templatemvvm.application

import android.app.Application
import android.content.Context

class TaskieApplication: Application() {

    companion object{
        private lateinit var instance: TaskieApplication

        fun getApplicationContext(): Context{
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}