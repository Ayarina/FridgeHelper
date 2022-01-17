package org.wit.fridgehelper.main

import android.app.Application
import org.wit.fridgehelper.models.User
import timber.log.Timber

class MainApp : Application() {

    lateinit var user: User

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("FridgeHelper started")
    }

}