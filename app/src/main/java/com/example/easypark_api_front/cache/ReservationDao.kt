package com.example.easypark_api_front.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations")
    suspend fun getAllReservations(): List<ReservationEntity>
}
