package com.example.easyshop.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast

// Extension function to validate email format
fun String.isEmailValid(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

// Extension function to validate password length
fun String.isPasswordValid(): Boolean {
    return this.length >= 6
}

// Extension function to validate name, email, and password
fun Context.validateInputs(name: String, email: String, password: String): Boolean {
    return when {
        name.isBlank() -> {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
            false
        }

        email.isBlank() -> {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            false
        }

        !email.isEmailValid() -> {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
            false
        }

        password.isBlank() -> {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            false
        }

        !password.isPasswordValid() -> {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                .show()
            false
        }

        else -> true
    }
}

// Extension function to validate email, and password
fun Context.validateLoginInput(email: String, password: String): Boolean {
    return when {
        email.isBlank() -> {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            false
        }

        !email.isEmailValid() -> {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
            false
        }

        password.isBlank() -> {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            false
        }

        !password.isPasswordValid() -> {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                .show()
            false
        }

        else -> true
    }
}

