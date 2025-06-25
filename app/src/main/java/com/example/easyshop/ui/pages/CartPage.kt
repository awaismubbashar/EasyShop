package com.example.easyshop.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.R
import com.example.easyshop.model.UserData
import com.example.easyshop.navigation.GlobalNavigation
import com.example.easyshop.ui.components.CartItemView
import com.example.easyshop.ui.composables.LoadingOverlay
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun CartPage(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    val userData = remember {
        mutableStateOf(UserData())
    }

    DisposableEffect(Unit) {
        val listener = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { it, _ ->
                if (it != null) {
                    val result = it.toObject(UserData::class.java)
                    result?.let { user ->
                        userData.value = user
                    }
                }
                isLoading = false
            }
        onDispose {
            listener.remove()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            "Your Cart",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.height(20.dp))
        if (!isLoading && userData.value.cartItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_cart),
                    contentDescription = "Empty Cart",
                    modifier = Modifier.size(100.dp),
//                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Your cart is empty", color = Color.Gray, fontWeight = FontWeight.SemiBold)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(
                    userData.value.cartItems.toList(),
                    key = { it.first }) { (productId, quantity) ->
                    CartItemView(modifier, productId, quantity)
                }
            }

            Spacer(Modifier.height(20.dp))
            if (userData.value.cartItems.isNotEmpty()) {
                Button(
                    onClick = {
                        GlobalNavigation.navController.navigate("checkout")
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    Text("Checkout", fontSize = 16.sp)
                }
            }
        }
    }
    // Show loading overlay
    if (isLoading) {
        LoadingOverlay()
    }
}