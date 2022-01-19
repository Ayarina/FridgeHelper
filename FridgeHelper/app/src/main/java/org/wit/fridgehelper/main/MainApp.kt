package org.wit.fridgehelper.main

import android.app.Application
import org.wit.fridgehelper.models.ProductModel
import org.wit.fridgehelper.models.User
import timber.log.Timber

class MainApp : Application() {

    lateinit var user: User
    val products = ArrayList<ProductModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("FridgeHelper started")

        products.add(ProductModel("Ejemplo1", "1€/u"))
    }

}