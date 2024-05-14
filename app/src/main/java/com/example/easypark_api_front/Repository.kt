package com.example.easypark_api_front

import com.example.easypark_api_front.Endpoint
import com.example.easypark_api_front.model.User

class Repository(private val endpoint: Endpoint) {



        suspend  fun getAllParkings()=endpoint.getParkings()
        suspend  fun registerUser(user:User)=endpoint.registerUser(user)
        suspend  fun getParkingById(id:Int)=endpoint.getParkingById(id)



}
