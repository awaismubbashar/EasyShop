package com.example.easyshop.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

object CartUtils {
    fun addItemToCart(productId: String, context: Context) {
        val userDoc = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity + 1

                val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)
                userDoc.update(updatedCart).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context, "Item added to cart")
                    } else {
                        showToast(context, "Failed adding item to the cart")
                    }
                }
            }
        }
    }

    fun removeFromCart(productId: String, context: Context, removeAll: Boolean = false) {
        val userDoc = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity - 1

                val updatedCart = if (updatedQuantity <= 0 || removeAll)
                    mapOf("cartItems.$productId" to FieldValue.delete())
                else
                    mapOf("cartItems.$productId" to updatedQuantity)

                userDoc.update(updatedCart).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context, "Item remove from cart")
                    } else {
                        showToast(context, "Failed to remove from the cart")
                    }
                }
            }
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}