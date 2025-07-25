package com.example.easyshop.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easyshop.utils.validateLoginInput
import com.example.easyshop.viewmodel.RegisterViewModel

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel: RegisterViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Login",
            style = TextStyle(
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )
        // Email Textview
        InputField(
            "Enter Email",
            email,
            { email = it },
            Icons.Default.Email,
            "email@mail.com",
            KeyboardType.Email
        )
        Spacer(Modifier.height(20.dp))
        // Password Textview
        InputField(
            "Enter Password",
            password,
            { password = it },
            Icons.Default.Lock,
            "******",
            KeyboardType.Email,
            isPassword = true
        )
        Spacer(Modifier.height(35.dp))
        // Login Button
        Button(
            onClick = {
                if (context.validateLoginInput(email = email, password = password)) {
                    isLoading = true
                    viewModel.loginUser(
                        email = email,
                        password = password,
                        onSuccess = {
                            isLoading = false
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            // Navigate to home or next screen
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onError = { message ->
                            isLoading = false
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(24.dp)
                )
            } else {
                Text("Login", fontSize = 19.sp)
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Don't have an account? Register",
                modifier = Modifier.clickable {
                    navController.navigate("registration") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                color = Color.Blue // Optional for a link-like style
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val fakeNavController = rememberNavController()
    LoginScreen(fakeNavController)
}