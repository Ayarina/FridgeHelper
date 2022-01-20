package org.wit.fridgehelper.main

import android.app.Application
import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.wit.fridgehelper.models.Location
import org.wit.fridgehelper.models.ProductMemStore
import org.wit.fridgehelper.models.ProductModel
import org.wit.fridgehelper.models.User
import timber.log.Timber

class MainApp : Application() {

    val products = mutableListOf<ProductModel>()
    lateinit var database: ProductMemStore
    lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("FridgeHelper started")
        FirebaseApp.initializeApp(applicationContext)
        database = ProductMemStore()
        auth = Firebase.auth
    }

}