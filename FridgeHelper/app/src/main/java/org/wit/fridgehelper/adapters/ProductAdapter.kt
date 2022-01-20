package org.wit.fridgehelper.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.fridgehelper.databinding.CardProductBinding
import org.wit.fridgehelper.models.ProductModel

interface ProductListener {
    fun onProductClick(product: ProductModel)
    fun valueUpdated(productChanged: ProductModel)
}

class ProductAdapter constructor(private var products: List<ProductModel>,
                                 private val listener: ProductListener) :
    RecyclerView.Adapter<ProductAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val product = products[holder.adapterPosition]
        holder.bind(product, listener)
    }

    override fun getItemCount(): Int = products.size

    class MainHolder(private val binding : CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, listener: ProductListener) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price
            binding.productQuantityPicker.number = "${product.quantity}"
            Picasso.get().load(Uri.parse(product.image)).resize(210,200).into(binding.productImage)
            binding.root.setOnClickListener { listener.onProductClick(product) }
            binding.productQuantityPicker.setOnValueChangeListener { view, oldValue, newValue ->
                product.quantity = binding.productQuantityPicker.number.toInt()
                listener.valueUpdated(product) }
            
        }
    }
}