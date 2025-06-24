package com.example.easyshop.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.easyshop.model.ProductModel
import com.example.easyshop.navigation.GlobalNavigation
import com.example.easyshop.utils.CartUtils
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CartItemView(modifier: Modifier, productId: String, quantity: Long) {
    val context = LocalContext.current
    var product by remember {
        mutableStateOf(ProductModel())
    }
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("data")
            .document("stock").collection("products")
            .document(productId)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.toObject(ProductModel::class.java)
                    result?.let {
                        product = it
                    }
                }
            }
    }


    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD)),
        onClick = {
            GlobalNavigation.navController.navigate("product-detail/" + product.id)
        }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Spacer(Modifier.width(5.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    product.title,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(5.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "$" + product.price,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        CartUtils.removeFromCart(product.id, context = context)
                    }) {
                        Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(quantity.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = {
                        CartUtils.addItemToCart(product.id, context = context)
                    }) {
                        Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            IconButton(onClick = {
                CartUtils.removeFromCart(product.id, context = context, removeAll = true)
            }) {
                Icon(Icons.Default.Delete, "Delete from cart")
            }
        }
    }
}