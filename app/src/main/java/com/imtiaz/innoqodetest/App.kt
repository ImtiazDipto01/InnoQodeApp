package com.imtiaz.innoqodetest

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp

class App: Application() {

    companion object {
        private lateinit var app: App

        fun getContext(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}