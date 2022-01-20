package org.wit.fridgehelper.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(var title: String? = null,
                    var lat: Double? = null,
                    var lng: Double? = null,
                    var zoom: Float? = null) : Parcelable