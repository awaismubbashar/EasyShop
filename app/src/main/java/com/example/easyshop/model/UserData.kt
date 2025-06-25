package com.example.easyshop.model

import okhttp3.Address

data class UserData(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val cartItems: Map<String, Long> = emptyMap()
)