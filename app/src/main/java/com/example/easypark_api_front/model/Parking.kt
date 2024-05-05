package com.example.easypark.model

import java.sql.Time

class Parking {
    var id: Int = 0
    var nom: String = ""
    var description: String = ""
    var address: String = ""
    var localization: String = ""
    var image: Int = 0
    var area: Float = 0f
    var open: Time = Time.valueOf("00:00:00")
    var close: Time = Time.valueOf("00:00:00")
    var rules: String = ""
    var pricePerHour: Float = 0f



}
