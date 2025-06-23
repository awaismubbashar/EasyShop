package com.example.easyshop.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.easyshop.navigation.GlobalNavigation

@Composable
fun ProductItemView(modifier: Modifier, product: ProductModel) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD)),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = {
            GlobalNavigation.navController.navigate("product-detail/" + product.id)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier
                    .height(100.dp)
            )
            Spacer(Modifier.height(5.dp))
            Text(
                product.title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$" + product.price,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "$" + product.actualPrice,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to cart"
                    )
                }
            }
        }
    }
}