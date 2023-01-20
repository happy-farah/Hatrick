package com.example.hatrick

data class Reservation(val reservationID:String? = null,val fieldID:String? = null,val ownerID:String? = null, val userID:String? = null, val public:String? = null, val fieldName:String? = null, val sportType:String? = null,
                       val nohours : Int? = null,val noplayers : Int? = null, val minNOPlayers : Int? = null, val reservationDate:String? = null, val startTime:String? = null, val finishTime:String? = null,val timearray: ArrayList<Int>? = null,
                       val pricePerPerson:Float? = null, val totalPrice:Float? = null)