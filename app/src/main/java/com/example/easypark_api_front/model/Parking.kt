package com.example.easypark.model

import java.sql.Time

data class Parking (
    var id: Int ,
    var nom: String ,
    var description: String ,
    var address: String ,
    var localization: String ,
    var image: String ,
    var area: Float ,
    var open: String ,
    var close: String ,
    var rules: String ,
    var price_per_hour: Float ,
    var available_slots: Int



)
