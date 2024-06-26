package com.example.easypark_api_front

import com.example.easypark_api_front.model.AuthResponse
import com.example.easypark_api_front.model.ReservationRequest
import com.example.easypark_api_front.model.User
import retrofit2.Response

class Repository(private val endpoint: Endpoint) {



        suspend  fun getAllParkings()=endpoint.getParkings()
        suspend  fun registerUser(user:User): Response<AuthResponse> =endpoint.registerUser(user)
        suspend  fun loginUser(user:User): Response<AuthResponse> =endpoint.loginUser(user)
        suspend fun logoutUser(token: String) = endpoint.logoutUser(token)
        suspend  fun getParkingById(id:Int)=endpoint.getParkingById(id)
        suspend  fun getParkingByType(type:String)=endpoint.getParkingByType(type)

        suspend fun createReservation(parkingId: Int, token: String, reservationRequest: ReservationRequest) =
                endpoint.createReservation(parkingId, token, reservationRequest)
        suspend fun getMyReservations(token: String) = endpoint.getMyReservations(token)

        suspend  fun verifyUser(name:String): Response<AuthResponse> =endpoint.verifyUser(name)


}
