package org.wit.fridgehelper.models

interface UserStore {

    fun writeNewUser(userId: String, name: String, email: String)
}