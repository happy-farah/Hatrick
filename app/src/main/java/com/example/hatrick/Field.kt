package com.example.hatrick

data class Field(
    val fieldID:String? = null,
    val ownerID:String? = null,
    val fieldName:String? = null,
    val phoneNumber:String? = null,
    val email:String? = null,
    val address:String? = null,
    val sportType: ArrayList<String>? = null,
    val capacity:Int? = null, val width:Int? = null,
    val length:Int? = null,
    val groundType:String? = null,
    val openingTimes:String? = null, val wholePrice:Float? = null,
    val pricePerPerson:Float? = null,
    val services:String? = null,
    val fieldRating:Int? = null,
    val feedBack:String?= null, val image:String?= null)