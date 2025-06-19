package com.example.easyshop.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.easyshop.ui.components.BannerView
import com.example.easyshop.ui.components.HeaderView

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        HeaderView(modifier)
        Spacer(Modifier.height(10.dp))
        BannerView(modifier = modifier.height(200.dp))
    }

}

@Preview
@Composable
fun HomePagerPreview() {}