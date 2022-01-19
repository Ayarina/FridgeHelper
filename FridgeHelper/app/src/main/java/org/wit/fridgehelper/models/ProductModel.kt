package org.wit.fridgehelper.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(var name: String = "",
                        var price: String = "",
                        var quantity: Int = 0): Parcelable
