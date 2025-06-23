package com.example.easyshop.model

data class UserData(
    val uid: String,
    val name: String,
    val email: String,
    val cartItems: Map<String, Long> = emptyMap()
)