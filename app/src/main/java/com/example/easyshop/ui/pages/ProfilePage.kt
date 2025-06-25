package com.example.easyshop.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyshop.R
import com.example.easyshop.model.UserData
import com.example.easyshop.navigation.GlobalNavigation
import com.example.easyshop.ui.composables.LoadingOverlay
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }
    val userData = remember {
        mutableStateOf(UserData())
    }

    var addressInput by remember {
        mutableStateOf(userData.value.address)
    }

    Firebase.firestore.collection("users")
        .document(Firebase.auth.currentUser?.uid!!)
        .get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result.toObject(UserData::class.java)
                result?.let { user ->
                    userData.value = user
                    addressInput = userData.value.address
                }
            }
            isLoading = false
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Your Cart",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.height(40.dp))
        Image(
            painter = painterResource(R.drawable.profile_icon),
            contentDescription = "Profile picture",
            modifier = Modifier
                .height(140.dp)
                .width(140.dp)
                .fillMaxWidth()
                .fillMaxSize()
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
        )
        Spacer(Modifier.height(20.dp))
        Text("Name", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(userData.value.name)
        Spacer(Modifier.height(20.dp))
        Text("Address", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        TextField(
            value = addressInput,
            onValueChange = {
                addressInput = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        Text("Email", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(userData.value.email)
        Spacer(Modifier.height(20.dp))
        Text("Cart count", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(userData.value.cartItems.size.toString())
        Spacer(Modifier.height(50.dp))
        Button(
            onClick = {
                val navController = GlobalNavigation.navController
                navController.popBackStack()
                navController.navigate("registration")
            }, modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text("Logout", fontSize = 16.sp)
        }
    }
    // Show loading overlay
    if (isLoading) {
        LoadingOverlay()
    }
}