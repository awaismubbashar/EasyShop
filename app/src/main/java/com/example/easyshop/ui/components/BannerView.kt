package com.example.easyshop.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun BannerView() {
    val firestore = FirebaseFirestore.getInstance()
    var bannerUrls by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    LaunchedEffect(Unit) {
        firestore.collection("data")
            .document("banners").get()
            .addOnCompleteListener { task ->
                bannerUrls = task.result.get("urls") as List<String>
            }
    }
    val pagerState = rememberPagerState(pageCount = {
        bannerUrls.size
    })

    if (bannerUrls.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                state = pagerState, pageSpacing = 24.dp, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) { page ->
                AsyncImage(
                    model = bannerUrls[page],
                    contentDescription = "Banner Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16))
                )
            }
            Spacer(Modifier.height(10.dp))
            DotsIndicator(
                dotCount = bannerUrls.size,
                type = ShiftIndicatorType(
                    dotsGraphic = DotGraphic(
                        color = Color.Red,
                        size = 6.dp
                    )
                ),
                pagerState = pagerState
            )
        }
    }
}

@Preview
@Composable
fun BannerViewPreview() {
}