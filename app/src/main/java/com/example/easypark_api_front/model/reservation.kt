package com.example.easypark_api_front.model

import com.example.easypark.model.Reservation
import com.example.easypark_api_front.cache.ReservationEntity

data class Reservation (
    var id: Int =0,
    var parking_name:String,
    var parking_id: Int ,
    var date:String,
    var parking_slot: String ,
    var parking_address: String ,
    var starthour:String = "",
    var duration:String=""
) {
    fun toEntity(): ReservationEntity {
        return ReservationEntity(
            id = this.id,
            parking_name = this.parking_name,
            parking_id = this.parking_id,
            date = this.date,
            parking_slot = this.parking_slot,
            parking_address = this.parking_address
        )
    }
}


data class ReservationRequest (
    val date: String,
    val start_hour: String,
    val duration: Int,
    val supported_type: String
)
