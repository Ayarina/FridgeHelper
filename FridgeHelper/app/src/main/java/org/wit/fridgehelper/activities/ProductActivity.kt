package org.wit.fridgehelper.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.fridgehelper.R
import org.wit.fridgehelper.databinding.ActivityProductBinding
import org.wit.fridgehelper.helpers.showImagePicker
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.ProductModel
import timber.log.Timber.i


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var app: MainApp

    var product = ProductModel()
    var edit: Boolean = false
    var delete: Boolean = false

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
            if(product.image != Uri.EMPTY) {
                binding.addImage.text = getString(R.string.button_updateImage)
                Picasso.get()
                    .load(product.image)
                    .into(binding.productImageAdd)
            }
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

        binding.addBestLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
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
            R.id.item_delete -> {
                if(!delete){
                    delete = true
                    Snackbar.make(binding.root,getString(R.string.confirm_delete), Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    app.products.remove(product)
                    finish()
                }
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
                            product.image = result.data!!.data!!
                            Picasso.get()
                                .load(product.image)
                                .into(binding.productImageAdd)
                            binding.addImage.text = getString(R.string.button_updateImage)
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }
}