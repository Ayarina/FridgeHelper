package org.wit.fridgehelper.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(var id: Long? = null,
                        var name: String? = null,
                        var price: String? = null,
                        var quantity: Int? = null,
                        var image: String? = null,
                        var location: Location? = null): Parcelable
