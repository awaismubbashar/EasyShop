package com.example.easyshop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.easyshop.model.CategoriesData
import com.example.easyshop.navigation.GlobalNavigation
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CategoriesView(modifier: Modifier = Modifier, onLoaded: () -> Unit) {

    val categories = remember {
        mutableStateOf<List<CategoriesData>>(emptyList())
    }
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("data")
            .document("stock").collection("categories")
            .get().addOnCompleteListener { task ->
                categories.value = task.result.documents.mapNotNull { doc ->
                    doc.toObject(CategoriesData::class.java)
                }
                onLoaded()
            }
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories.value) { item ->
            CategoriesItem(item)
        }
    }
}

@Composable
fun CategoriesItem(item: CategoriesData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = {
            GlobalNavigation.navController.navigate("category-products/" + item.id)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(50.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(item.name, textAlign = TextAlign.Center)
        }
    }
}