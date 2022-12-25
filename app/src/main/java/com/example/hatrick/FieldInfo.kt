package com.example.hatrick

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class FieldInfo : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_info)
        val FieldIntent = intent
        val fieldName = FieldIntent.getStringExtra("fieldName")
        findViewById<TextView>(R.id.fieldNameTxt).text = fieldName
        val phone = FieldIntent.getStringExtra("phoneNumber")
        findViewById<TextView>(R.id.phoneNumber).text = phone
        val email = FieldIntent.getStringExtra("email")
        findViewById<TextView>(R.id.email).text = email
        val location = FieldIntent.getStringExtra("address")
        findViewById<TextView>(R.id.location).text = location
        val length = FieldIntent.getIntExtra("length",0)
        findViewById<TextView>(R.id.length).text = length.toString()
        val width = FieldIntent.getIntExtra("width",0)
        findViewById<TextView>(R.id.width).text = width.toString()
        val capacity = FieldIntent.getIntExtra("capacity",0)
        findViewById<TextView>(R.id.capacity).text = capacity.toString()
        val groundtype = FieldIntent.getStringExtra("groundType")
        findViewById<TextView>(R.id.groundType).text = groundtype
        val services = FieldIntent.getStringExtra("services")
        findViewById<TextView>(R.id.services).text = services
        val image = FieldIntent.getStringExtra("image")
        val uri = image?.toUri()
        Picasso.get().load(uri).into(findViewById<ImageView>(R.id.img))
    }

}
