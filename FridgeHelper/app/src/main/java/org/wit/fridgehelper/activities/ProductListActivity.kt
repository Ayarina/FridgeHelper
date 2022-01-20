package org.wit.fridgehelper.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.fridgehelper.R
import org.wit.fridgehelper.adapters.ProductAdapter
import org.wit.fridgehelper.adapters.ProductListener
import org.wit.fridgehelper.databinding.ActivityProductListBinding
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.Location
import org.wit.fridgehelper.models.ProductModel

class ProductListActivity : AppCompatActivity(), ProductListener {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        //RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ProductAdapter(app.products, this)

        binding.recyclerView.adapter?.notifyDataSetChanged()
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ProductActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onProductClick(product: ProductModel) {
        val launcherIntent = Intent(this, ProductActivity::class.java)
        launcherIntent.putExtra("product_edit", product)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun valueUpdated(productChanged: ProductModel) {
        app.database.updateProduct(app.auth.currentUser!!.uid, productChanged)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
    }


}