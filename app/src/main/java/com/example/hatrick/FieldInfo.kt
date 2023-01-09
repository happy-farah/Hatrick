package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
        val FieldData = FirebaseFirestore.getInstance()
        val sporttype = FieldIntent.getStringExtra("sportType")
        val fieldName = FieldIntent.getStringExtra("fieldName")
        findViewById<TextView>(R.id.fieldNameTxt).text = fieldName
        val openingTimes = FieldIntent.getStringExtra("openingTimes")
        findViewById<TextView>(R.id.openeingTime).text = openingTimes
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
        val pricePerPerson = FieldIntent.getFloatExtra("pricePerPerson", 0F)
        findViewById<TextView>(R.id.pricePerPerson).text = pricePerPerson.toString()
        val wholePrice = FieldIntent.getFloatExtra("wholePrice", 0F)
        findViewById<TextView>(R.id.price).text = wholePrice.toString()
        val groundtype = FieldIntent.getStringExtra("groundType")
        findViewById<TextView>(R.id.groundType).text = groundtype
        val services = FieldIntent.getStringExtra("services")
        findViewById<TextView>(R.id.services).text = services
        val image = FieldIntent.getStringExtra("image")
        val uri = image?.toUri()
        Picasso.get().load(uri).into(findViewById<ImageView>(R.id.img))
        val fieldID = FieldIntent.getStringExtra("fieldID")


        val createGame = findViewById<Button>(R.id.createGameBtn)
        if (sporttype=="Football")
        {
            createGame.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
        }
        if (sporttype=="Basketball")
        {
            createGame.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (sporttype=="Tennis")
        {
            createGame.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (sporttype=="Handball")
        {
            createGame.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (sporttype=="Badminton")
        {
            createGame.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (sporttype=="Volleyball")
        {
            createGame.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
        }
        
        createGame.setOnClickListener {
            val intent = Intent(this , CreateAGame::class.java)
            intent.putExtra("fieldID",fieldID)
            intent.putExtra("fieldName",fieldName)
            intent.putExtra("sportType",sporttype)
            startActivity(intent)
        }


    }

}
