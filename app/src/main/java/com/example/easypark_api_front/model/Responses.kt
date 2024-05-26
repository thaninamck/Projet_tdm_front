package com.example.easypark_api_front.model

data class UserResponse (
    val id: Int,
    val full_name: String,
    val phone: String
)

data class AuthResponse(
    val user: UserResponse,
    val token: String
)