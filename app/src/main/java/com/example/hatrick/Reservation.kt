package com.example.hatrick

import java.util.Date

data class Reservation(val fieldID:String? = null, val userID:String? = null, val public:String? = null, val fieldName:String? = null, val sportType:String? = null, val nOHours : Int? = null,val nOPlayers : Int? = null, val minNOPlayers : Int? = null, val gameDate:String? = null, val gameTime:String? = null, val totalPrice:Float? = null)
