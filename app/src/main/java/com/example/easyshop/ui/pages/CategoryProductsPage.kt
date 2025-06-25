package com.example.easyshop.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.R
import com.example.easyshop.ui.components.ProductItemView
import com.example.easyshop.model.ProductModel
import com.example.easyshop.ui.composables.LoadingOverlay
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CategoryProductsPage(modifier: Modifier, categoryId: String?) {
    var isLoading by remember { mutableStateOf(true) }
    val productsList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("data")
            .document("stock").collection("products")
            .whereEqualTo("category", categoryId)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.documents.mapNotNull { doc ->
                        doc.toObject(ProductModel::class.java)
                    }
                    productsList.value = result
//                    productsList.value = result.plus(result).plus(result)
                }
                isLoading = false
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!isLoading && productsList.value.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_cart),
                    contentDescription = "No Products",
//                    tint = Color.Gray,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "No products found",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(productsList.value.chunked(2)) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (index == 0) Modifier.padding(top = 5.dp) else Modifier
                            ),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item.forEach {
                            ProductItemView(modifier = Modifier.weight(1f), product = it)
                        }
                        if (item.size == 1) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
    // Show loading overlay
    if (isLoading) {
        LoadingOverlay()
    }
}