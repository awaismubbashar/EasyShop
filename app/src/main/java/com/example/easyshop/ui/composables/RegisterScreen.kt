package com.example.easyshop.ui.composables

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easyshop.utils.validateInputs
import com.example.easyshop.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val registerViewModel: RegisterViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "SignUp",
                style = TextStyle(
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(Modifier.height(30.dp))
        InputField(
            "Enter Name",
            name,
            { name = it },
            Icons.Default.AccountCircle,
            "Joseph",
            KeyboardType.Text
        )
        Spacer(Modifier.height(15.dp))
        InputField(
            "Enter Email",
            email,
            { email = it },
            Icons.Default.Email,
            "email@mail.com",
            KeyboardType.Email
        )
        Spacer(Modifier.height(15.dp))
        InputField(
            "Enter Password",
            password,
            { password = it },
            Icons.Default.Lock,
            "******",
            KeyboardType.Password,
            isPassword = true
        )
        Spacer(Modifier.height(35.dp))
        Button(
            onClick = {
                if (context.validateInputs(name = name, password = password, email = email)) {
                    isLoading = true
                    registerViewModel.registerUser(
                        name = name,
                        email = email,
                        password = password,
                        onSuccess = {
                            isLoading = false
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT)
                                .show()
                            // ✅ Navigate to LoginScreen
                            navController.navigate("login") {
                                popUpTo("registration") {
                                    inclusive = true
                                } // Optional: clears back stack
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
                Text("Register", fontSize = 19.sp)
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Already have an account? Login",
                modifier = Modifier.clickable {
                    navController.navigate("login") {
                        popUpTo("registration") { inclusive = true }
                    }
                },
                color = Color.Blue // Optional for a link-like style
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val fakeNavController = rememberNavController()
    RegisterScreen(navController = fakeNavController)
}