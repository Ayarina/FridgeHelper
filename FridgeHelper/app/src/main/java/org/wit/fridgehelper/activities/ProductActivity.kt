package org.wit.fridgehelper.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.wit.fridgehelper.R
import org.wit.fridgehelper.databinding.ActivityProductBinding
import org.wit.fridgehelper.helpers.showImagePicker
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.ProductModel
import timber.log.Timber.i


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    var product = ProductModel()
    private lateinit var app: MainApp
    var edit: Boolean = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        var index: Int = 0

        if (intent.hasExtra("product_edit")) {
            edit = true
            product = intent.extras?.getParcelable("product_edit")!!
            binding.productNameAdd.setText(product.name)
            binding.productPriceAdd.setText(product.price)
            binding.productQuantityAdd.setText(product.quantity.toString())
            binding.btnAdd.text = getString(R.string.button_editProduct)
            binding.addImage.text = getString(R.string.button_updateImage)
            index = app.products.indexOf(product)
        }

        binding.productQuantityAdd.setText("0")
        binding.btnAdd.setOnClickListener() {
            product.name = binding.productNameAdd.text.toString()
            product.price = binding.productPriceAdd.text.toString()
            product.quantity = binding.productQuantityAdd.text.toString().toInt()
            if(product.name.isNotEmpty()) {
                if(!edit) {
                    app.products.add(product.copy())
                    i("Added product: $product")
                }
                else {
                    app.products[index] = product
                    i("Edited product: $product")
                }
                setResult(RESULT_OK)
                finish()
            }
            else{
                Snackbar.make(it,getString(R.string.enter_name), Snackbar.LENGTH_LONG)
                    .show()
            }

        }

        binding.addImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}