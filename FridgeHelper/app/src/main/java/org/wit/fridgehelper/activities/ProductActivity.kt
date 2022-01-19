package org.wit.fridgehelper.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.fridgehelper.databinding.ActivityProductBinding
import timber.log.Timber.i


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title


        binding.btnAdd.setOnClickListener() {
            //TODO: implementation

        }

    }
}