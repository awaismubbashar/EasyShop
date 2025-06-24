package com.example.easyshop.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.easyshop.model.ProductModel
import com.example.easyshop.utils.CartUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailPage(modifier: Modifier, productId: String) {

    val context = LocalContext.current
    var product by remember {
        mutableStateOf(ProductModel())
    }
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("data")
            .document("stock").collection("products").document(productId)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        product = result
                    }
                }
            }
    }
    val pagerState = rememberPagerState(pageCount = {
        product.images.size
    })

    Column(
        modifier = modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        Text(product.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        //Pager
        if (product.images.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 24.dp,
                ) { page ->
                    AsyncImage(
                        model = product.images[page],
                        contentDescription = "Banner Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(16))
                    )
                }
                Spacer(Modifier.height(10.dp))
                DotsIndicator(
                    dotCount = product.images.size,
                    type = ShiftIndicatorType(
                        dotsGraphic = DotGraphic(
                            color = MaterialTheme.colorScheme.primary,
                            size = 6.dp
                        )
                    ),
                    pagerState = pagerState
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "$" + product.price,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = TextStyle(
                    textDecoration = TextDecoration.LineThrough
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "$" + product.actualPrice,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add to Favorite"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                CartUtils.addItemToCart(productId, context = context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text("Add to Cart", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Product description : ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(product.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))
        if (product.otherDetails.isNotEmpty())
            Text("Other Product details : ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        product.otherDetails.forEach { (key, value) ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) {
                Text("$key : ", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(value, fontSize = 16.sp)
            }
        }
    }
}