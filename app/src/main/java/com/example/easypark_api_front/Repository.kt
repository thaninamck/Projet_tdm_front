package com.example.easypark_api_front

import com.example.easypark_api_front.Endpoint
import com.example.easypark_api_front.model.AuthResponse
import com.example.easypark_api_front.model.User
import retrofit2.Response

class Repository(private val endpoint: Endpoint) {



        suspend  fun getAllParkings()=endpoint.getParkings()
        suspend  fun registerUser(user:User): Response<AuthResponse> =endpoint.registerUser(user)
        suspend  fun loginUser(user:User): Response<AuthResponse> =endpoint.loginUser(user)
        suspend fun logoutUser(token: String) = endpoint.logoutUser(token)
        suspend  fun getParkingById(id:Int)=endpoint.getParkingById(id)
        suspend fun getMyReservations(token: String) = endpoint.getMyReservations(token)


}
