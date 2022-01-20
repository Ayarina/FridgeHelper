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
import org.wit.fridgehelper.models.Location
import org.wit.fridgehelper.models.ProductModel
import timber.log.Timber.i


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var app: MainApp

    var product = ProductModel(0, "", "", 0, "", Location("",0.0, 0.0, 0.0f))
    var edit: Boolean = false
    var delete: Boolean = false
    var location = Location("Mercadona", 52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        var index: Int = 0

        if (intent.hasExtra("product_edit")) {
            index = app.products.indexOf(product)
            edit = true
            product = intent.extras?.getParcelable("product_edit")!!
            binding.productNameAdd.setText(product.name)
            binding.productPriceAdd.setText(product.price)
            binding.productQuantityAdd.setText(product.quantity.toString())
            binding.btnAdd.text = getString(R.string.button_editProduct)
            if(product.image == null)
                product.image = Uri.EMPTY.toString()
            if(product.image != Uri.EMPTY.toString()) {
                binding.addImage.text = getString(R.string.button_updateImage)
                Picasso.get()
                    .load(Uri.parse(product.image))
                    .into(binding.productImageAdd)
            }
        }

        binding.btnAdd.setOnClickListener() {
            product.name = binding.productNameAdd.text.toString()
            product.price = binding.productPriceAdd.text.toString()
            product.quantity = binding.productQuantityAdd.text.toString().toInt()
            if(product.image == null)
                product.image = Uri.EMPTY.toString()
            if(product.name == null)
                product.name = ""
            if(product.name!!.isNotEmpty()) {
                if(!edit) {
                    app.database.addProduct(app.auth.currentUser!!.uid, product.copy())
                    i("Added product: $product")
                }
                else {
                    app.database.updateProduct(app.auth.currentUser!!.uid, product.copy())
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
            if(product.location?.zoom != 0.0f){
                location.lat = product.location?.lat
                location.lng = product.location?.lng
                location.zoom = product.location?.zoom
            }

            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
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
                    app.database.deleteProduct(app.auth.currentUser!!.uid, product.copy())
                    app.products.remove(product)
                    setResult(RESULT_OK)
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
                            product.image = result.data!!.data!!.toString()
                            Picasso.get()
                                .load(Uri.parse(product.image))
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
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                            product.location = location
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
}       }