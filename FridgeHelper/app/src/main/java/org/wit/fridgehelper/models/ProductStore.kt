package org.wit.fridgehelper.models

interface ProductStore {
    fun addProduct(userId: String, product: ProductModel)
    fun updateProduct(userId: String, product: ProductModel)
    fun deleteProduct(userId: String, product: ProductModel)
}