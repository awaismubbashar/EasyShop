package com.example.easyshop.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.easyshop.ui.components.ProductItemView
import com.example.easyshop.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CategoryProductsPage(modifier: Modifier, categoryId: String?) {

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
            }
    }

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