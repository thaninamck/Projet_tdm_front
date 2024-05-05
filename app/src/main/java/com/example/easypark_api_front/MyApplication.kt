package com.example.easypark_api_front

import android.app.Application
import com.example.easypark_api_front.Endpoint
import com.example.easypark_api_front.Repository

class MyApplication:Application() {
    private val endpoint by lazy{ Endpoint.createEndpoint()}
    val repository by lazy{ Repository(endpoint) }
}