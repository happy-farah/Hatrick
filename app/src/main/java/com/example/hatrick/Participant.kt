package com.example.hatrick

data class Participant(val reservationID:String? = null, val userID:String? = null, val totalPrice:Float? = null,
                       val public:String? = null, val fieldName:String? = null, val sportType:String? = null,
                       val noplayers : Int? = null, val reservationDate:String? = null, val startTime:String? = null, val finishTime:String? = null)