package org.wit.fridgehelper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.fridgehelper.R
import org.wit.fridgehelper.databinding.ActivityProductListBinding
import org.wit.fridgehelper.main.MainApp

class ProductListActivity : AppCompatActivity() {
    //TODO añadir recycler view
    private lateinit var binding: ActivityProductListBinding
    private lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title

        app = application as MainApp

    }
}