package com.example.easyshop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyshop.ui.composables.Home
import com.example.easyshop.ui.composables.LoginScreen
import com.example.easyshop.ui.composables.RegisterScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
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
    }
}