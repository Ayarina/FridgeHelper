package org.wit.fridgehelper.models.interfaces

interface UserStore {
    fun writeNewUser(userId: String, name: String, email: String)
}