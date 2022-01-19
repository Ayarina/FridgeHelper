package org.wit.fridgehelper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.fridgehelper.R
import org.wit.fridgehelper.adapters.ProductAdapter
import org.wit.fridgehelper.databinding.ActivityProductListBinding
import org.wit.fridgehelper.main.MainApp

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title

        app = application as MainApp

        //RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ProductAdapter(app.products)

    }
}