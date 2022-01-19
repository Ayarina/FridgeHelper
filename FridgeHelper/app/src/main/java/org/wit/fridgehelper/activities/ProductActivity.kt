package org.wit.fridgehelper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.fridgehelper.R
import org.wit.fridgehelper.databinding.ActivityProductBinding
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.ProductModel
import timber.log.Timber.i


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    var product = ProductModel()
    private lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title

        app = application as MainApp

        binding.productQuantityAdd.setText("0")
        binding.btnAdd.setOnClickListener() {
            product.name = binding.productNameAdd.text.toString()
            product.price = binding.productPriceAdd.text.toString()
            product.quantity = binding.productQuantityAdd.text.toString().toInt()
            if(product.name.isNotEmpty()) {
                app.products.add(product.copy())
                i("Added product: $product")
                setResult(RESULT_OK)
                finish()
            }
            else{
                Snackbar.make(it,getString(R.string.enter_name), Snackbar.LENGTH_LONG)
                    .show()
            }

        }

    }
}