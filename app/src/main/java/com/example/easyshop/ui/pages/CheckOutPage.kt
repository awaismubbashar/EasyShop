package com.example.easyshop.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.model.ProductModel
import com.example.easyshop.model.UserData
import com.example.easyshop.ui.composables.LoadingOverlay
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@Composable
fun CheckOutPage(modifier: Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    val userData = remember {
        mutableStateOf(UserData())
    }
    val productList = remember {
        mutableStateListOf(ProductModel())
    }
    val tax = remember { mutableStateOf(0f) }
    val subTotal = remember { mutableStateOf(0f) }
    val discount = remember { mutableStateOf(0f) }
    val total = remember { mutableStateOf(0f) }
    val discountPercent = 5.0f;
    val taxPercent = 10.0f;

    fun calculatePrice() {
        productList.forEach {
            if (it.price.isNotEmpty()) {
                val qty = userData.value.cartItems[it.id] ?: 0
                subTotal.value += (it.price.toFloat() * qty)
            }
        }
        discount.value = "%.2f".format(subTotal.value * (discountPercent / 100)).toFloat()
        tax.value = "%.2f".format(subTotal.value * (taxPercent / 100)).toFloat()

        total.value = "%.2f".format(subTotal.value - discount.value + tax.value).toFloat()
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserData::class.java)
                    result?.let { user ->
                        userData.value = user

                        FirebaseFirestore.getInstance().collection("data")
                            .document("stock").collection("products")
                            .whereIn("id", userData.value.cartItems.keys.toList())
                            .get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val products = task.result.toObjects(ProductModel::class.java)
                                    productList.addAll(products)
                                    calculatePrice()
                                }
                            }
                    }
                }
                isLoading = false
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text(
            "Your Cart",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.height(15.dp))
        HorizontalDivider()
        Spacer(Modifier.height(15.dp))
        RowCheckOutItem("SubTotal", subTotal.value)
        Spacer(Modifier.height(10.dp))
        RowCheckOutItem("Tax (+)", tax.value)
        Spacer(Modifier.height(10.dp))
        RowCheckOutItem("Discount (-)", discount.value)
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(20.dp))
        Text("To Pay", fontSize = 14.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(Modifier.height(5.dp))
        Text(total.value.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
    // Show loading overlay
    if (isLoading) {
        LoadingOverlay()
    }
}

@Composable
private fun RowCheckOutItem(key: String, value: Float) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(key, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Text("$${value.toString()}", fontSize = 16.sp)
    }
}

