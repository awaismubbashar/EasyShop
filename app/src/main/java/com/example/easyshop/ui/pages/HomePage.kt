package com.example.easyshop.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.ui.components.BannerView
import com.example.easyshop.ui.components.CategoriesView
import com.example.easyshop.ui.components.HeaderView

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        HeaderView(modifier)
        Spacer(Modifier.height(10.dp))
        BannerView()
        Spacer(Modifier.height(10.dp))
        Text(
            "Categories", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(10.dp))
        CategoriesView()

    }

}

@Preview
@Composable
fun HomePagerPreview() {
    HomePage()
}