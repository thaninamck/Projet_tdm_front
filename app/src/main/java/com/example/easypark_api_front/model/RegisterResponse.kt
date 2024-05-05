package com.example.easypark_api_front.model

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val data: UserData
)