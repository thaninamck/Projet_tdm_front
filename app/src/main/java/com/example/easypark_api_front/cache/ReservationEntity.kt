package com.example.easypark_api_front.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.easypark_api_front.model.Reservation

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val parking_name: String,
    val parking_id: Int,
    val date: String,
    val parking_slot: String,
    val parking_address: String
) {
    fun toDomain(): Reservation {
        return Reservation(
            id = id,
            parking_name = parking_name,
            parking_id = parking_id,
            date = date,
            parking_slot = parking_slot,
            parking_address = parking_address
        )
    }
}
