package org.wit.fridgehelper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.fridgehelper.databinding.CardProductBinding
import org.wit.fridgehelper.models.ProductModel

class ProductAdapter constructor(private var products: List<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val product = products[holder.adapterPosition]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    class MainHolder(private val binding : CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price
            binding.productQuantityPicker.number = product.quantity.toString()
        }
    }
}