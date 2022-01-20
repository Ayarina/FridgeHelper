package org.wit.fridgehelper.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var username: String? = null,
                var email: String? = null) : Parcelable
