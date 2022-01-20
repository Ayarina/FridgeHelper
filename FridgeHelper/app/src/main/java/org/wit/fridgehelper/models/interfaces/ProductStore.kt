package org.wit.fridgehelper.models.interfaces

import org.wit.fridgehelper.models.ProductModel

interface ProductStore {
    fun addProduct(userId: String, product: ProductModel)
    fun updateProduct(userId: String, product: ProductModel)
    fun deleteProduct(userId: String, product: ProductModel)
}