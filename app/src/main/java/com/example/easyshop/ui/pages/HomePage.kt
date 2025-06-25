package com.example.easyshop.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.ui.components.BannerView
import com.example.easyshop.ui.components.CategoriesView
import com.example.easyshop.ui.components.HeaderView
import com.example.easyshop.ui.composables.LoadingOverlay

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    var headerLoaded by remember { mutableStateOf(false) }
    var bannerLoaded by remember { mutableStateOf(false) }
    var categoriesLoaded by remember { mutableStateOf(false) }

    val isLoading = !(headerLoaded && bannerLoaded && categoriesLoaded)

    LaunchedEffect(headerLoaded, bannerLoaded, categoriesLoaded) {
        Log.d(
            "LOADING_DEBUG",
            "Flags - Header: $headerLoaded, Banner: $bannerLoaded, Categories: $categoriesLoaded"
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        HeaderView(onLoaded = { headerLoaded = true })
        Spacer(Modifier.height(10.dp))
        BannerView(onLoaded = { bannerLoaded = true })
        Spacer(Modifier.height(10.dp))
        Text(
            "Categories", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(10.dp))
        CategoriesView(onLoaded = { categoriesLoaded = true })

    }
    if (isLoading) {
        LoadingOverlay()
    }
}

@Preview
@Composable
fun HomePagerPreview() {
    HomePage()
}