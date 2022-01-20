package org.wit.fridgehelper.models

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i
import java.util.*

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ProductMemStore: ProductStore, UserStore {

    private var database: DatabaseReference = Firebase.database("https://fridgehelper-563cd-default-rtdb.europe-west1.firebasedatabase.app/").reference

    //For reading purposes
    fun getDatabaseReference(): DatabaseReference{
        return database
    }

    fun getProductsOfUser(userId: String, products: MutableList<ProductModel>){
        var empty = products.isEmpty()

        val productListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var aux = 0
                var size = products.size
                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(ProductModel::class.java)
                        if(aux <= size - 1)
                            products[aux] = product!!
                        else
                            products.add(product!!)
                        i("Set Listener on $product")
                        aux++
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        database.child("users").child(userId).child("products").addValueEventListener(productListener)

    }

    override fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
        //A empty product is added
        val product = ProductModel(0, "Example", "1€/u", 1, "", Location("Carrefour", 0.0, 0.0, 0.0f))
        database.child("users").child(userId).child("products").child(product.id.toString()).setValue(product)

    }

    override fun addProduct(userId: String, product: ProductModel) {
        product.id = generateRandomId()
        database.child("users").child(userId).child("products").child(product.id.toString()).setValue(product)
    }

    override fun updateProduct(userId: String, product: ProductModel) {
        database.child("users").child(userId).child("products").child(product.id.toString()).setValue(product)
    }

    override fun deleteProduct(userId: String, product: ProductModel) {
        database.child("users").child(userId).child("products").child(product.id.toString()).removeValue()
    }

}