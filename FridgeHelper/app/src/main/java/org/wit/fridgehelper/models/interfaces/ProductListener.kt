package org.wit.fridgehelper.models.interfaces

import org.wit.fridgehelper.models.ProductModel

interface ProductListener {
    fun onProductClick(product: ProductModel)
    fun valueUpdated(productChanged: ProductModel)
}