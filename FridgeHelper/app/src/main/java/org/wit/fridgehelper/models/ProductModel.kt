package org.wit.fridgehelper.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(var id: Long = 0,
                        var name: String = "",
                        var price: String = "",
                        var quantity: Int = 0,
                        var image: Uri = Uri.EMPTY,
                        var location: Location = Location()): Parcelable
