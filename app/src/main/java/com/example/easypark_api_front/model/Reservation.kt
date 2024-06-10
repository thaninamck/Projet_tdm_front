package com.example.easypark_api_front.model

data class Reservation (
    var id: Int =0,
    var parking_name:String,
    var parking_id: Int ,
    var date:String,
    var parking_slot: String ,
    var parking_address: String ,
    var starthour:String,
    var duration:String=""

)