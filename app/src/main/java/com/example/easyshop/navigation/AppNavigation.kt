package com.example.easyshop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyshop.ui.composables.Home
import com.example.easyshop.ui.composables.LoginScreen
import com.example.easyshop.ui.composables.RegisterScreen
import com.example.easyshop.ui.pages.CategoryProductsPage
import com.example.easyshop.ui.pages.ProductDetailPage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    val isLogin = Firebase.auth.currentUser != null
    val firstPage = if (isLogin) "home" else "registration"

    NavHost(navController = navController, startDestination = firstPage) {

        composable("registration") {
            RegisterScreen(navController = navController)
        }

        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            Home()
        }
        composable("category-products/{categoryId}") {
            val categoryId = it.arguments?.getString("categoryId")
            CategoryProductsPage(modifier, categoryId)
        }
        composable("product-detail/{categoryId}") {
            val categoryId = it.arguments?.getString("categoryId")
            ProductDetailPage(modifier, categoryId ?: "")
        }
    }
}

object GlobalNavigation {
    lateinit var navController: NavController
}