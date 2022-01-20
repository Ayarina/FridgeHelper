package org.wit.fridgehelper.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.fridgehelper.databinding.ActivityProductMapBinding
import org.wit.fridgehelper.databinding.ContentProductMapBinding
import org.wit.fridgehelper.main.MainApp
import org.wit.fridgehelper.models.ProductModel

class ProductMapActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityProductMapBinding
    private lateinit var contentBinding: ContentProductMapBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        contentBinding = ContentProductMapBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }


    fun configureMap() {
        map.setOnMarkerClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true
        app.products.forEach {
            val loc = LatLng(it.location?.lat!!, it.location?.lng!!)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location?.zoom!!))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val foundProduct: ProductModel? = app.products.find{
            p -> p.id == marker.tag
        }
        contentBinding.currentName.text = foundProduct?.name
        contentBinding.currentPrice.text = foundProduct?.price
        Picasso.get().load(Uri.parse(foundProduct?.image)).into(contentBinding.currentImage)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

}