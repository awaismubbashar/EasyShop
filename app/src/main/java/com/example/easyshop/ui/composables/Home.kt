package com.example.easyshop.ui.composables


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.easyshop.ui.pages.CartPage
import com.example.easyshop.ui.pages.FavoritePage
import com.example.easyshop.ui.pages.HomePage
import com.example.easyshop.ui.pages.ProfilePage
import com.example.easyshop.viewmodel.RegisterViewModel

@Composable
fun Home() {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val user by registerViewModel.user.collectAsState()
    var selectedIndex by remember { mutableStateOf(0) }
    val navigationItemsList = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Default.Favorite
        ),
        NavigationItem(
            title = "Cart",
            icon = Icons.Default.ShoppingCart
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Person
        ),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItemsList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier
                .padding(innerPadding),
            selectedIndex = selectedIndex
        )
    }


}

@Composable
private fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomePage(modifier)
        1 -> FavoritePage(modifier)
        2 -> CartPage(modifier)
        3 -> ProfilePage(modifier)
    }
    Text(text = "Name: ")
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector
)